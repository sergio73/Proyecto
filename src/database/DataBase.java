/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Do basic interactions with the database
 * 
 * @author Sergio Delgado Baringo
 */
public class DataBase {
    private String db;
    private String login;
    private String pw;
    private String server;
    
    private Connection connection;
    
    /**
     * Initialise the database with parameters
     * 
     * @param db
     * @param login
     * @param pw
     * @param server 
     */
    public DataBase(String db, String login, String pw, String server){
        this.db = db;
        this.login = login;
        this.pw = pw;
        this.server = server;
    }
    
    /**
     * Try to open the database
     */
    public boolean open(){
        boolean status = false;
        
        try{
            //Load driver
            Class.forName("com.mysql.jdbc.Driver");
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            this.connection = DriverManager.getConnection(this.server + this.db, this.login, this.pw);
            
            status = true;
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return status;
    }
    
    /**
     * Close the database
     */
    public void close(){
        try{
            this.connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Execute a insert query
     * @param query
     * @throws java.sql.SQLException
     */
    public void execute(String query) throws SQLException{
        Statement stmt = this.connection.createStatement();
        stmt.executeUpdate(query);
    }
    
    /**
     * Execute a search query
     * @param query
     * @return 
     * @throws java.sql.SQLException 
     */
    public ResultSet find(String query) throws SQLException{        
        Statement st = null;
        ResultSet rs = null;
        
        st = this.connection.createStatement();
        rs = st.executeQuery(query);
        
        return rs;
    }
}
