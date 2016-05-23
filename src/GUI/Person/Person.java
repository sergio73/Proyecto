/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Person;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Main class for person, create all person fields.
 * @author Sergio Delgado Baringo
 */
public class Person extends JFrame implements ActionListener, WindowListener {
    protected String[] labels = { "Nombre", "Apellidos", "Cargo", "Direccion", "Telefono" };
    protected JTextField[] fields = new JTextField[labels.length];
    
    protected JPanel panel;
    
    public Person(){
        this.setTitle("Personas");
        
        this.initializeComponents();
        
        this.pack();
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        
        this.setVisible(true);
    }
    
    /**
     * If the fields are valid return true otherwise false
     * @return 
     */
    protected boolean validateFields(){
        
        if(!fields[0].getText().trim().matches(".{3,50}")){
            JOptionPane.showMessageDialog(this, "El nombre tiene que tener entre 3 y 50 caracteres.", "Error.", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if(!fields[1].getText().trim().matches(".{3,50}")){
            JOptionPane.showMessageDialog(this, "Los apellidos tienen que tener entre 3 y 50 caracteres.", "Error.", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if(!fields[2].getText().trim().matches(".{0,50}")){
            JOptionPane.showMessageDialog(this, "El cargo tiene que tener maximo 50 caracteres.", "Error.", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if(!fields[3].getText().trim().matches(".{3,50}")){
            JOptionPane.showMessageDialog(this, "La direccion tiene que tener entre 3 y 50 caracteres.", "Error.", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if(!fields[4].getText().trim().matches("[0-9]{9}")){
            JOptionPane.showMessageDialog(this, "El telefono tiene que tener 9 digitos.", "Error.", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    /**
     * Initialize window components
     */
    private void initializeComponents(){
        this.panel = (JPanel) this.getContentPane();
        this.panel.setLayout(new GridLayout(6, 2, 10, 20));
        this.panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        for(int i=0; i<labels.length; i++){
            JLabel label = new JLabel();
            label.setText(labels[i] + ":");
            this.panel.add(label);
            
            JTextField text = new JTextField();
            this.panel.add(text);
            
            fields[i] = text;
        }
        
        JButton button = new JButton();
        button.setText("Cancelar");
        button.setActionCommand("cancel");
        button.addActionListener(this);
        this.panel.add(button);
        
        button = new JButton();
        button.setText("Aceptar");
        button.setActionCommand("accept");
        button.addActionListener(this);
        this.panel.add(button);
        
        this.addWindowListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {}
    @Override
    public void windowClosing(WindowEvent e) {}
    @Override
    public void windowOpened(WindowEvent e) {}
    @Override
    public void windowClosed(WindowEvent e) {}
    @Override
    public void windowIconified(WindowEvent e) {}
    @Override
    public void windowDeiconified(WindowEvent e) {}
    @Override
    public void windowActivated(WindowEvent e) {}
    @Override
    public void windowDeactivated(WindowEvent e) {}
}
