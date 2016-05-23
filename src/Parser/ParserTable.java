/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parser;



import database.DataBaseManager;
import database.Record;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * Parser to convert a table in to a XML Document
 * @author Sergio Delgado Baringo
 */
public class ParserTable {
    Class table;
    String tableName;
    Object[] os;
    
    /**
     * Create the parser with all the objects loaded from the database
     * @param table 
     */
    public ParserTable(Class table){
        this.table = table;
        this.os = DataBaseManager.getInstance().findAll(table, "");
        this.tableName = ((database.annotations.Table)table.getAnnotation(database.annotations.Table.class)).name();
    }
    
    /**
     * Create the parser with the specified objects
     * @param table
     * @param os 
     */
    public ParserTable(Class table, Object[] os){
        this.table = table;
        this.os = os;
        this.tableName = ((database.annotations.Table)table.getAnnotation(database.annotations.Table.class)).name();
    }
    
    /**
     * Convert the table into a XML document
     * @param path 
     */
    public void SaveXML(String path){
        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            
            Element rootElement = doc.createElement("Table" + this.tableName.substring(0,1).toUpperCase() + this.tableName.substring(1));
            doc.appendChild(rootElement);
            
            for(int i=0; i<this.os.length; i++){
                Object o = this.os[i];
                
                Element node = doc.createElement(tableName);
                rootElement.appendChild(node);
                
                Record[] rs = DataBaseManager.getRecords(o);
                for(int j=0; j<rs.length; j++){
                    Record r = rs[j];
                    
                    Element subNode = doc.createElement(r.getName());
                    subNode.appendChild(doc.createTextNode(r.getValue() == null ? "NULL" : r.getValue().toString()));
                    
                    node.appendChild(subNode);
                }
            }
            
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            
            StreamResult result = new StreamResult(new File(path));
            transformer.transform(source, result);
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ParserTable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(ParserTable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(ParserTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
