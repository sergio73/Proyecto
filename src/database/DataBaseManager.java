/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import annotations.Table;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This manager automatize all sql query
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
     */
    public void save(Object o){
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
     */
    public void update(Object o){
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
            set.next();
            
            Record[] records = this.getRecords(o);
            for(int i=0; i<records.length; i++){
                Object value = set.getObject(records[i].getName());
                
                //Set values
                if(value != null){
                    
                    if(records[i].getType() == int.class){
                        records[i].getField().set(o, Integer.parseInt(value.toString()));
                    }else if(records[i].getType() == float.class){
                        records[i].getField().set(o, Float.parseFloat(value.toString()));
                    }else if(records[i].getType() == double.class){
                        records[i].getField().set(o, Double.parseDouble(value.toString()));
                    }else if(records[i].getType() == String.class){
                        records[i].getField().set(o, value.toString());
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
        } finally {
            this.dataBase.close();
            return o;
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

                        if(records[i].getType() == int.class){
                            records[i].getField().set(o, Integer.parseInt(value.toString()));
                        }else if(records[i].getType() == float.class){
                            records[i].getField().set(o, Float.parseFloat(value.toString()));
                        }else if(records[i].getType() == double.class){
                            records[i].getField().set(o, Double.parseDouble(value.toString()));
                        }else if(records[i].getType() == String.class){
                            records[i].getField().set(o, value.toString());
                        }
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
        } finally {
            this.dataBase.close();
            return objects.toArray();
        }
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
    private Record[] getRecords(Object o){
        Field[] fields = o.getClass().getFields();
        Record[] records = new Record[fields.length];
        
        for(int i=0; i<fields.length; i++){
            records[i] = new Record(fields[i], o);
        }
        
        return records;
    }
}
