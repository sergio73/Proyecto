/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import annotations.Column;
import annotations.Table;

/**
 *
 * @author Sergio Delgado Baringo
 */
@Table(name = "vivienda")
public class LivingPlace {
    @Column(name = "id", type = int.class)
    public int id;
    @Column(name = "habitaciones", type = int.class)
    public int rooms;
    @Column(name = "propiedad_id", type = int.class)
    public int property_id;
}
