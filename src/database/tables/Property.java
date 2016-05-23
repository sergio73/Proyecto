/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import database.annotations.Column;
import database.annotations.Table;

/**
 * Table property
 * @author Sergio Delgado Baringo
 */
@Table(name = "propiedad")
public class Property{
    @Column(name = "id", type = int.class)
    public int id;
    @Column(name = "num_portal", type = int.class)
    public int portalNumber;
    @Column(name = "letra", type = char.class)
    public char letter;
    @Column(name = "porcentaje_gastos", type = float.class)
    public float percentExpenses;
    @Column(name = "tipo", type = byte.class)
    public byte type;
    @Column(name = "vive", type = int.class)
    public int live_id;
    @Column(name = "dueno", type = int.class)
    public int owner_id;
}
