package GUI;


import javax.swing.table.DefaultTableModel;


/**
 * This will avoid changes in the table
 * @author Sergio Delgado Baringo
 */
public class NonEditableModel extends DefaultTableModel {

    public NonEditableModel(int row, int col) {
        super(row, col);
    }
    
    
    NonEditableModel(Object[][] data, String[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}