/*
 * $Id: DefaultTableUI.java,v 1.4 2008-03-10 21:16:51 sanderk Exp $
 *
 * Copyright (C) 2005 Sander Kooijmans
 *
 */

package nl.gogognome.swing.plaf;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;

import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTableUI;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import sun.swing.DefaultLookup;

/**
 * The default look and feel for a table. 
 */
public class DefaultTableUI extends BasicTableUI {
    
    public static ComponentUI createUI(JComponent c) {
        return new DefaultTableUI();
    }

    public void installUI(JComponent c) {
        super.installUI(c);
        JTable table = (JTable) c;

        // Install alternating background renderer for the columns.
        HashSet<Class<?>> classes = new HashSet<Class<?>>();
        for (int col=0; col<table.getColumnCount(); col++) {
            Class clazz = table.getColumnClass(col);
            if (!classes.contains(clazz)) {
                classes.add(clazz);
                TableCellRenderer renderer = table.getDefaultRenderer(clazz);
                table.setDefaultRenderer(clazz, new AlternatingBackgroundRenderer(renderer));
            }
            
            table.getColumnModel().getColumn(col).addPropertyChangeListener(new PropertyChangeListener() {

                public void propertyChange(PropertyChangeEvent evt) {
                    if ("cellRenderer".equals(evt.getPropertyName())) {
                        if (!(evt.getNewValue() instanceof AlternatingBackgroundRenderer)) {
                            ((TableColumn) evt.getSource()).setCellRenderer(
                                new AlternatingBackgroundRenderer((TableCellRenderer)evt.getNewValue()));
                        }
                    }
                }
                
            });
        }
    }
    
    protected void installKeyboardActions() {
        super.installKeyboardActions();
        InputMap inputMap = (InputMap)DefaultLookup.get(table, this, "Table.ancestorInputMap"); 
        inputMap.remove(KeyStroke.getKeyStroke("ENTER"));
    }
}
