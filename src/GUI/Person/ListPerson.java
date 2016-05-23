/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Person;

import GUI.EventListener;
import GUI.List;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * Prepare a view for the persons
 * @author Sergio Delgado Baringo
 */
public class ListPerson extends List{
   
    public ListPerson(){
        super();
        
        this.model.setColumnIdentifiers(new String[]{ "ID", "Nombre", "Apellidos", "Cargo", "Direccion", "Telefono" });
        update();
        
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 0;
        this.gbc.gridy = 1;
        this.gbc.weightx = 0.5;
        
        JButton button = new JButton();
        button.setText("Eliminar");
        button.setActionCommand("delete");
        button.addActionListener(this);
        this.panel.add(button, this.gbc);
        
        this.gbc.fill = GridBagConstraints.HORIZONTAL;
        this.gbc.gridx = 1;
        this.gbc.gridy = 1;
        
        button = new JButton();
        button.setText("Editar");
        button.setActionCommand("edit");
        button.addActionListener(this);
        this.panel.add(button, this.gbc);
    }
    
    /**
     * Update the table
     */
    private void update(){
        for (int i = this.model.getRowCount() - 1; i >= 0; i--) {
            this.model.removeRow(i);
        }
        
        Object[] persons = database.DataBaseManager.getInstance().findAll(database.tables.Person.class, "");
        for(int i=0; i<persons.length; i++){
            database.tables.Person p = (database.tables.Person) persons[i];
            this.model.addRow(new Object[]{ p.id, p.name, p.lastName, p.position, p.direction, p.phone });
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "delete": delete(); break;
            case "edit": edit(); break;
        }
    }
    
    /**
     * Gives the id of the row selected
     * @return 
     */
    private int getIdSelected(){
        if(this.table.getSelectedRow() == -1)
            return -1;
        
        return Integer.parseInt(this.table.getValueAt(this.table.getSelectedRow(), 0).toString()); 
    }
    
    /**
     * Deletes a person
     */
    private void delete(){
        int id = getIdSelected();
        if(id == -1){
            JOptionPane.showMessageDialog(this, "No se ha seleccionado ninguna fila.", "Error.", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            database.DataBaseManager.getInstance().deleteById(database.tables.Person.class, id);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "No se ha podido eliminar la persona.", "Error.", JOptionPane.ERROR_MESSAGE);
        }
        update();
    }
    
    /**
     * Edit a person
     */
    private void edit(){
        int id = getIdSelected();
        if(id == -1){
            JOptionPane.showMessageDialog(this, "No se ha seleccionado ninguna fila.", "Error.", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        EditPerson edit = new EditPerson((database.tables.Person)database.DataBaseManager.getInstance().findById(database.tables.Person.class, id));
        //Set and create the listener that will update the table when the dialog closes
        edit.setListener(new EventListener() {
            @Override
            public void event(EventListener.Type type) {
                if(type == Type.CLOSE)
                    update();
            }
        });
        
    }
}
