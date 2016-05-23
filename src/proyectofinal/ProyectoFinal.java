/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal;

import database.DataBase;
import database.DataBaseManager;

/**
 * 
 * @author Sergio Delgado Baringo
 */
public class ProyectoFinal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Initialize the dataBaseManager
        DataBaseManager.getInstance().setDataBase(new DataBase("xe", "root", "root", "jdbc:oracle:thin:@localhost:1521:"));
        new GUI.Main();
    }
    
}
