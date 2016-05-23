/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.Person.ListPerson;
import GUI.Person.NewPerson;
import GUI.Properties.ListTypeProperties;
import GUI.Properties.ListPostalCodeProperties;
import GUI.Properties.ListProperties;
import Parser.ParserTable;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

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
    
    /**
     * Initialize window components
     */
    private void initializeComponents(){
        String buttonsText[] = {"Alta persona", "Ver personas", "Listar propiedades", "Buscar propiedades por codigo postal", "Buscar propiedades por tipo de local", "Convertir XML"};
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
        switch(Integer.parseInt(e.getActionCommand())){
            case 0: new NewPerson(); break;
            case 1: new ListPerson(); break;
            case 2: new ListProperties(); break;
            case 3: 
                String postalCode = "";
                do{
                    postalCode = JOptionPane.showInputDialog("Introduce el codigo postal a buscar: ");
                    if(postalCode == null){
                        return;
                    }
                }while(!postalCode.matches("[0-9]{5}"));
                new ListPostalCodeProperties(postalCode);
            break;
            case 4: 
                String input = (String) JOptionPane.showInputDialog(null, "Tipo de local:", "Elegir local", JOptionPane.QUESTION_MESSAGE, null, new String[]{"Vivienda", "Local", "Oficinas"}, 0); // Initial choice
                
                if(input!=null){
                    int type = -1;
                    
                    if(input == "Vivienda")
                        type = 0;
                    else if(input == "Local")
                        type = 1;
                    else
                        type = 2;
                    
                    new ListTypeProperties(type); 
                }
            break;
            case 5:
                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(new FileNameExtensionFilter("Archivo XML (.xml)", ".xml"));
                int status = chooser.showSaveDialog(this);
                
                if(status == JFileChooser.APPROVE_OPTION){
                    ParserTable parserTable = new ParserTable(database.tables.Person.class);
                    parserTable.SaveXML(chooser.getSelectedFile().getAbsolutePath()+".xml");
                }
            break;
        }
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
