/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

/**
 * Represents a window with a list
 * @author Sergio Delgado Baringo
 */
public class List extends JFrame implements ActionListener, WindowListener {
    protected GridBagConstraints gbc = new GridBagConstraints();
    protected JPanel panel;
    protected JTable table;
    protected NonEditableModel model;
    
    public List(){
        this.setTitle("Listar");
        
        this.initializeComponents();
        
        this.pack();
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        
        this.setVisible(true);
    }
    
    /**
     * Initialize window components
     */
    private void initializeComponents(){
        this.panel = (JPanel) this.getContentPane();
        this.panel.setLayout(new GridBagLayout());
        this.panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        this.table = new JTable();
        this.model = new NonEditableModel(0, 0);
        this.table.setModel(this.model);
        
        this.gbc.fill = GridBagConstraints.BOTH;
        this.gbc.gridx = 0;
        this.gbc.gridy = 0;
        this.gbc.weightx = 1;
        this.gbc.weighty = 1;
        this.gbc.gridwidth = 2;
        this.add(new JScrollPane(this.table), this.gbc);
        
        //Reset
        this.gbc.weightx = 0;
        this.gbc.weighty = 0;
        this.gbc.gridwidth = 1;
        
        this.addWindowListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {

    }

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
