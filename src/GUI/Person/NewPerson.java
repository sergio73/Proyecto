/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Person;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Window to create a new person
 * @author Sergio Delgado Baringo
 */
public class NewPerson extends Person{
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "cancel": dispose(); break;
            case "accept": save(); break;
        }
    }
    
    /**
     * Save the person into the database
     */
    private void save(){
        if(!this.validateFields()){
            return;
        }
        
        //"Nombre", "Apellidos", "Cargo", "Direccion", "Telefono" 
        database.tables.Person p = new database.tables.Person();
        
        p.name = this.fields[0].getText();
        p.lastName = this.fields[1].getText();
        if(this.fields[2].getText().equals("")){
            p.position = null;
        }else{
            p.position = this.fields[2].getText();
        }
        p.direction = this.fields[3].getText();
        p.phone = this.fields[4].getText();
        
        try {
            database.DataBaseManager.getInstance().save(p);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "No se ha podido insertar la persona.", "Error.", JOptionPane.ERROR_MESSAGE);
        }
        
        dispose();
    }
}
