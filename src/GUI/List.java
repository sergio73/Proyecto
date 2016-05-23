/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Sergio Delgado Baringo
 */
public class Main extends JFrame implements ActionListener, WindowListener {
    JPanel panel;
    JButton buttons[];
    
    public Main(){
        this.setTitle("Proyecto final");
        
        this.initializeComponents();
        
        this.pack();
        this.setSize(400, 600);
        this.setLocationRelativeTo(null);
        
        this.setVisible(true);
    }
    
    private void initializeComponents(){
        String buttonsText[] = {"Alta persona", "Baja persona", "Modificar persona", "Listar propiedades", "Buscar propiedades por codigo postal", "Buscar propiedades por tipo de local", "Convertir XML"};
        this.buttons = new JButton[buttonsText.length];
        
        this.panel = (JPanel) this.getContentPane();
        this.panel.setLayout(new GridLayout(buttonsText.length, 1, 0, 20));
        this.panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        for(int i=0; i<buttonsText.length; i++){
            buttons[i] = new JButton();
            buttons[i].setText(buttonsText[i]);
            buttons[i].setActionCommand(Integer.toString(i));
            buttons[i].addActionListener(this);
            this.panel.add(buttons[i]);
        }
        
        this.addWindowListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
    
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
