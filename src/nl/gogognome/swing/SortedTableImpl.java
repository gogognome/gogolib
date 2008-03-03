/*
 * $Id: SortedTableImpl.java,v 1.1 2008-03-03 20:06:11 sanderk Exp $
 *
 * Copyright (C) 2005 Sander Kooijmans
 *
 */

package nl.gogognome.swing;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 * This class implements a table that allows the user to sort the columns.
 * 
 * <p>This class is needed, because simply adding a {@link TableSorter} to a {@link JTable}
 * messes the selection mechanism of the {@link JTable}.
 * 
 * @author Sander Kooijmans
 */
class SortedTableImpl implements SortedTable {

    /** The table model that sorts the rows. */
    private TableSorter tableSorter;
    
    /** The actual table. */
    private JTable table;

    /** 
     * The selection model. The row numbers in the selection model refer to the unsorted rows.
     */
    private ListSelectionModel selectionModel;
    
    /**
     * Constructs a table based on the specified table model. The table allows selection of rows,
     * not of columns.
     * @param tableModel the table model
     */
    public SortedTableImpl(SortedTableModel tableModel) {
        super();
        table = new JTable();
        tableSorter = new TableSorter(tableModel, table.getTableHeader());
        table.setModel(tableSorter);
        selectionModel = new ListSelectionModelWrapper(table.getSelectionModel());
        
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);
        
        TableColumnModel columnModel = table.getColumnModel();
        for (int i=0; i<tableModel.getColumnCount(); i++) {
            TableCellRenderer renderer = tableModel.getRendererForColumn(i);
            if (renderer != null) {
                columnModel.getColumn(i).setCellRenderer(renderer);
            }
        }
    }

    /**
     * @see SortedTable#getComponent()
     */
    public JComponent getComponent() {
        return table;
    }

    /**
     * @see SortedTable#getSelectionModel()
     */
    public ListSelectionModel getSelectionModel() {
        return selectionModel;
    }
    
    private class ListSelectionModelWrapper implements ListSelectionModel {

        private ListSelectionModel wrappedModel;
        
        public ListSelectionModelWrapper(ListSelectionModel wrappedModel) {
            this.wrappedModel = wrappedModel;
        }
        
        public void addListSelectionListener(ListSelectionListener listener) {
            wrappedModel.addListSelectionListener(listener);
        }

        public void addSelectionInterval(int index0, int index1) {
            if (index0 > index1) {
                int t = index0;
                index0 = index1;
                index1 = t;
            }
            assert index0 <= index1;
            
            for (int i=index0; i<=index1; i++) {
                int index = tableSorter.viewIndex(i);
                wrappedModel.addSelectionInterval(index, index);
            }
        }

        public void clearSelection() {
            wrappedModel.clearSelection();
        }

        public int getAnchorSelectionIndex() {
            return tableSorter.modelIndex(wrappedModel.getAnchorSelectionIndex());
        }

        public int getLeadSelectionIndex() {
            return tableSorter.modelIndex(wrappedModel.getLeadSelectionIndex());
        }

        public int getMaxSelectionIndex() {
            int result = -1;
            for (int i=0; i<table.getRowCount(); i++) {
                if (wrappedModel.isSelectedIndex(i)) {
                    result = Math.max(result, tableSorter.modelIndex(i));
                }
            }
            return result;
        }

        public int getMinSelectionIndex() {
            int result = Integer.MAX_VALUE;
            for (int i=0; i<table.getRowCount(); i++) {
                if (wrappedModel.isSelectedIndex(i)) {
                    result = Math.min(result, tableSorter.modelIndex(i));
                }
            }
            if (result == Integer.MAX_VALUE) {
                result = -1;
            }
            return result;
        }

        public int getSelectionMode() {
            return wrappedModel.getSelectionMode();
        }

        public boolean getValueIsAdjusting() {
            return wrappedModel.getValueIsAdjusting();
        }

        public void insertIndexInterval(int index, int length, boolean before) {
            // TODO Auto-generated method stub
            System.out.println("insertIndexInterval(" + index + ", " + length + ", " + before);
        }

        public boolean isSelectedIndex(int index) {
            return wrappedModel.isSelectedIndex(tableSorter.viewIndex(index));
        }

        public boolean isSelectionEmpty() {
            return wrappedModel.isSelectionEmpty();
        }

        public void removeIndexInterval(int index0, int index1) {
            if (index0 > index1) {
                int t = index0;
                index0 = index1;
                index1 = t;
            }
            assert index0 <= index1;
            
            for (int i=index0; i<=index1; i++) {
                int index = tableSorter.viewIndex(i);
                wrappedModel.removeIndexInterval(index, index);
            }
        }

        public void removeListSelectionListener(ListSelectionListener listener) {
            wrappedModel.removeListSelectionListener(listener);
        }

        public void removeSelectionInterval(int index0, int index1) {
            if (index0 > index1) {
                int t = index0;
                index0 = index1;
                index1 = t;
            }
            assert index0 <= index1;
            
            for (int i=index0; i<=index1; i++) {
                int index = tableSorter.viewIndex(i);
                wrappedModel.removeIndexInterval(index, index);
            }
        }

        public void setAnchorSelectionIndex(int index) {
            wrappedModel.setAnchorSelectionIndex(tableSorter.viewIndex(index));
        }

        public void setLeadSelectionIndex(int index) {
            wrappedModel.setLeadSelectionIndex(tableSorter.viewIndex(index));
        }

        public void setSelectionInterval(int index0, int index1) {
            clearSelection();
            if (index0 > index1) {
                int t = index0;
                index0 = index1;
                index1 = t;
            }
            assert index0 <= index1;
            
            for (int i=index0; i<=index1; i++) {
                int index = tableSorter.viewIndex(i);
                wrappedModel.addSelectionInterval(index, index);
            }
         }

        public void setSelectionMode(int selectionMode) {
            wrappedModel.setSelectionMode(selectionMode);            
        }

        public void setValueIsAdjusting(boolean valueIsAdjusting) {
            wrappedModel.setValueIsAdjusting(valueIsAdjusting);
        }
        
    }
}
