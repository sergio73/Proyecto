/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import database.annotations.Table;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This manager automatize all sql queries
 * 
 * @author Sergio Delgado Baringo
 */
public class DataBaseManager {
    private static DataBaseManager _manager = null;
    /**
     * Get the current instance of DataBaseManager. Singleton
     * @return 
     */
    public static DataBaseManager getInstance(){
        if(_manager == null){
            _manager = new DataBaseManager();
        }
        
        return _manager;
    }
    private DataBaseManager(){ }
    
    private DataBase dataBase;
    
    /**
     * Set the database to query
     * @param dataBase 
     */
    public void setDataBase(DataBase dataBase){
        this.dataBase = dataBase;
    }
    
    /**
     * Get the actual dataBase
     * @return 
     */
    public DataBase getDataBase(){
        return this.dataBase;
    }
    
    /**
     * Check if the value have to be escaped, set to null or be a number
     * @param r
     * @return 
     */
    private String valueToDatabase(Record r){
        String value = "";
        
        if(r.getValue() == null){
            value += "NULL";
        }else if(r.getType() == String.class){
            value += "'" + r.getValue().toString() + "'";
        }else{
            value += r.getValue().toString();
        }
        
        return value;
    }
    
    /**
     * Like oracle database does not have an autoincrement value this just looks the last id
     * @param table
     * @return 
     */
    private int getLastId(String table){
        this.dataBase.open();
        
        try {
            ResultSet set = this.dataBase.find("SELECT id FROM "+ table +" WHERE ROWNUM = 1 ORDER BY id DESC");
            set.next();
            int id = set.getInt("id");
            set.close();
            this.dataBase.close();
            
            return id;
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.dataBase.close();
        return -1;
    }
    
    /**
     * Inserts an object into the database
     * @param o 
     * @throws java.sql.SQLException 
     */
    public void save(Object o) throws SQLException{
        int nextId = this.getLastId(this.getTableName(o)) + 1;
        
        String query = "INSERT INTO ";
        
        query += this.getTableName(o);
        query += " (";
        
        Record[] records = this.getRecords(o);
        for(int i=0; i<records.length; i++){
            
            query += records[i].getName() + ",";
        }
        query = query.substring(0, query.length() - 1);    
        query += ") VALUES (";
        
        for(int i=0; i<records.length; i++){
            //Put next id
            if(records[i].getName().equals("id")){
                query += nextId + ",";
                continue;
            }
            
            query += this.valueToDatabase(records[i]) + ",";
        }
        query = query.substring(0, query.length() - 1);
        query += ")";
        
        //Change the id of the record
        try {
            o.getClass().getField("id").set(o, nextId);
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(DataBaseManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(DataBaseManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(DataBaseManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DataBaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.dataBase.open();
        this.dataBase.execute(query);
        this.dataBase.close();
    }
    
    /**
     * Update a record in the database
     * @param o 
     * @throws java.sql.SQLException 
     */
    public void update(Object o) throws SQLException{
        String query = "UPDATE ";
        
        query += this.getTableName(o);
        query += " SET ";
        
        Record[] records = this.getRecords(o);
        int id = 0;
        for(int i=0; i<records.length; i++){
            //Save the id and skip record
            if(records[i].getName().equals("id")){
                id = (int)records[i].getValue();
                continue;
            }
            
            query += records[i].getName() + "=";
            query += this.valueToDatabase(records[i]) + ",";
        }
        
        query = query.substring(0, query.length() - 1);
        query += " WHERE id = ";
        query += id;
        
        this.dataBase.open();
        this.dataBase.execute(query);
        this.dataBase.close();
    }
    
    /**
     * Searchs an object in the database and remove it
     * @param o 
     * @throws java.sql.SQLException 
     */
    public void delete(Object o) throws SQLException{
        
        Record[] records = this.getRecords(o);
        int id = 0;
        
        for(int i=0; i<records.length; i++){
            //Save the id and skip record
            if(records[i].getName().equals("id")){
                id = (int)records[i].getValue();
                break;
            }
        }
        
        this.deleteById(o.getClass(), id);
    }
    
    /**
     * Removes an element by their id
     * @param type
     * @param id 
     */
    public void deleteById(Class type, int id) throws SQLException{
        String query = "DELETE FROM ";
        
        query += ((Table)type.getAnnotation(Table.class)).name();
        query += " WHERE id = " + id;
        
        this.dataBase.open();
        this.dataBase.execute(query);
        this.dataBase.close();
    }
    
    /**
     * Find one record by id
     * @param type
     * @param id
     * @return 
     */
    public Object findById(Class type, int id){
        Object o = null;
        try {
            o = type.newInstance();
            this.dataBase.open();
            
            ResultSet set = this.dataBase.find("SELECT * FROM "+ this.getTableName(o) +" WHERE id = " + id);
            if(set.next()){

                Record[] records = this.getRecords(o);
                for(int i=0; i<records.length; i++){
                    Object value = set.getObject(records[i].getName());

                    //Set values
                    if(value != null){

                        assignField(records[i], o, value);
                    }
                }          
            }
            set.close();
        } catch (InstantiationException ex) {
            Logger.getLogger(DataBaseManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DataBaseManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseManager.class.getName()).log(Level.SEVERE, null, ex);
        } 
        this.dataBase.close();
        return o;
    }
    
    /**
     * Assign a field the value in the correct type
     * @param r
     * @param o
     * @param v
     * @throws IllegalArgumentException
     * @throws IllegalAccessException 
     */
    private void assignField(Record r, Object o, Object v) throws IllegalArgumentException, IllegalAccessException{
        if(r.getType() == int.class){
            r.getField().set(o, Integer.parseInt(v.toString()));
        }else if(r.getType() == float.class){
            r.getField().set(o, Float.parseFloat(v.toString()));
        }else if(r.getType() == double.class){
            r.getField().set(o, Double.parseDouble(v.toString()));
        }else if(r.getType() == String.class){
            r.getField().set(o, v.toString());
        }else if(r.getType() == char.class){
            r.getField().set(o, v.toString().charAt(0));
        }
    }
    
    /**
     * Find all the records in the database
     * @param type
     * @param where Optional, sql sintaxis
     * @return 
     */
    public Object[] findAll(Class type, String where){
        ArrayList<Object> objects = new ArrayList<>();
        
        try {
            this.dataBase.open();
            
            ResultSet set = this.dataBase.find("SELECT * FROM "+ ((Table)type.getAnnotation(Table.class)).name() + (where == "" ? "" : " WHERE " + where));
            
            while(set.next()){
                Object o = type.newInstance();
            
                Record[] records = this.getRecords(o);
                for(int i=0; i<records.length; i++){
                    Object value = set.getObject(records[i].getName());

                    //Set values
                    if(value != null){

                        assignField(records[i], o, value);
                    }
                }
                
                objects.add(o);
            }
            
            set.close();
        } catch (InstantiationException ex) {
            Logger.getLogger(DataBaseManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DataBaseManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dataBase.close();
        return objects.toArray();
    }
    
    /**
     * Get the table name by the annotation
     * @param o
     * @return 
     */
    private String getTableName(Object o){
        Table table = o.getClass().getAnnotation(Table.class);
        return table.name();
    }
    
    /**
     * Get the columns metadata
     * @param o
     * @return 
     */
    public static Record[] getRecords(Object o){
        Field[] fields = o.getClass().getFields();
        Record[] records = new Record[fields.length];
        
        for(int i=0; i<fields.length; i++){
            records[i] = new Record(fields[i], o);
        }
        
        return records;
    }
}
