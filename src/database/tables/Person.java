/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import database.annotations.Column;
import database.annotations.Table;


/**
 * Table person
 * @author Sergio Delgado Baringo
 */
@Table(name = "persona")
public class Person {
    @Column(name = "id", type = int.class)
    public int id;
    @Column(name = "nombre", type = String.class)
    public String name;
    @Column(name = "apellidos", type = String.class)
    public String lastName;
    @Column(name = "cargo", type = String.class)
    public String position;
    @Column(name = "direccion", type = String.class)
    public String direction;
    @Column(name = "telefono", type = String.class)
    public String phone;
}
