/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal;

import database.DataBase;

/**
 *
 * @author Sergio Delgado Baringo
 */
public class ProyectoFinal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DataBase.getInstance().init("local", "root", "root", "localhost");
        GUI.Main main = new GUI.Main();
    }
    
}
