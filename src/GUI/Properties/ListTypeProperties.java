/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Properties;

import GUI.List;
import java.awt.GridBagConstraints;
import javax.swing.JButton;

/**
 * Search properties by type
 * @author Sergio Delgado Baringo
 */
public class ListTypeProperties extends List{
    
    public ListTypeProperties(int type){
        super();
        
        this.model.setColumnIdentifiers(new String[]{ "ID", "Nº Portal", "Letra", "Gastos %", "Dueño", "Vive" });

        Object[] properties = database.DataBaseManager.getInstance().findAll(database.tables.Property.class, "tipo = " + type);
        
        for(int i=0; i<properties.length; i++){
            database.tables.Property p = (database.tables.Property) properties[i];
            
            database.tables.Person owns = (database.tables.Person)database.DataBaseManager.getInstance().findById(database.tables.Person.class, p.owner_id);
            database.tables.Person live = (database.tables.Person)database.DataBaseManager.getInstance().findById(database.tables.Person.class, p.live_id);
            
            this.model.addRow(new Object[]{ p.id, p.portalNumber, p.letter, p.percentExpenses, owns.name + " " + owns.lastName, live.name == null ? "VACIO" : live.name + " " + live.lastName });
        }
    }
}
