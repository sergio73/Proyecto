/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import annotations.Column;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a row in the database
 * @author Sergio Delgado Baringo
 */
public class Record {
    private Object value;
    private String name;
    private Class type;
    private Field field;
    
    public Record(Field field, Object o){
        Column col = field.getAnnotation(Column.class);
        this.name = col.name();
        this.type = col.type();
        this.field = field;
        
        try {
            this.value = field.get(o);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Record.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Record.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return value;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the type
     */
    public Class getType() {
        return type;
    }

    /**
     * @return the field
     */
    public Field getField() {
        return field;
    }
    
}
