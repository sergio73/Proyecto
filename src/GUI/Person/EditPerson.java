/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Person;

import GUI.EventListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * This window will load fields of a person and will update the database
 * @author Sergio Delgado Baringo
 */
public class EditPerson extends Person{
    database.tables.Person person;
    EventListener listener;
    
    public EditPerson(database.tables.Person person){
        this.person = person;
        this.fields[0].setText(person.name);
        this.fields[1].setText(person.lastName);
        this.fields[2].setText(person.position == null ? "" : person.position);
        this.fields[3].setText(person.direction);
        this.fields[4].setText(person.phone);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "cancel": dispose(); break;
            case "accept": save(); break;
        }
    }
    
    /**
     * Listener of all events
     * @param listener 
     */
    public void setListener(EventListener listener){
        this.listener = listener;
    }
    
    /**
     * Update the person into the database
     */
    private void save(){
        if(!this.validateFields()){
            return;
        }
        
        this.person.name = this.fields[0].getText();
        this.person.lastName = this.fields[1].getText();
        if(this.fields[2].getText().equals("")){
            this.person.position = null;
        }else{
            this.person.position = this.fields[2].getText();
        }
        this.person.direction = this.fields[3].getText();
        this.person.phone = this.fields[4].getText();
        
        try {
            database.DataBaseManager.getInstance().update(this.person);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "No se ha podido actualizar la persona.", "Error.", JOptionPane.ERROR_MESSAGE);
        }
        dispose();
    }
    
    @Override
    public void windowClosed(WindowEvent e) {
        if(this.listener != null){
            this.listener.event(EventListener.Type.CLOSE);
        }
    }
}
