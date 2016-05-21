/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;

/**
 *
 * @author Sergio Delgado Baringo
 */
public class DataBase {
    private static DataBase _instance = null;
    public static DataBase getInstance(){
        if(_instance == null){
            _instance = new DataBase();
        }
        
        return _instance;
    }
    private DataBase() { }
    
    private String db;
    private String login;
    private String pw;
    private String server;
    
    public static void init(String db, String loign, String pw, String server){
        
    }
}
