/*
 * $Id: DefaultTableUI.java,v 1.2 2007-11-19 19:45:18 sanderk Exp $
 *
 * Copyright (C) 2005 Sander Kooijmans
 *
 */

package nl.gogognome.swing.plaf;

import java.awt.Color;
import java.awt.Component;
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
    
    /** Background color for the odd rows. */
    private final static Color COLOR_ODD_ROWS = new Color(240, 240, 255);
    
    public static ComponentUI createUI(JComponent c) {
        return new DefaultTableUI();
    }

    public void installUI(JComponent c) {
        super.installUI(c);
        JTable table = (JTable) c;
        HashSet classes = new HashSet();
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
    
    /**
     * This class wraps a {@link TableCellRenderer}. It uses the wrapped renderer to obtain
     * a table cell renderer component and sets the background color of that component alternatingly.
     */
    private static class AlternatingBackgroundRenderer implements TableCellRenderer {

        private TableCellRenderer wrappedRenderer;
        
        public AlternatingBackgroundRenderer(TableCellRenderer wrappedRenderer) {
            this.wrappedRenderer = wrappedRenderer;
        }
        
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = wrappedRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            component.setBackground(row % 2 == 0 ? Color.WHITE : COLOR_ODD_ROWS);
            return component;
        }
        
    }
}
