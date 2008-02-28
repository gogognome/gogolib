/*
 * $Id: JSortedTable.java,v 1.1 2008-02-28 20:40:16 sanderk Exp $
 *
 * Copyright (C) 2005 Sander Kooijmans
 *
 */

package nl.gogognome.swing;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

/**
 * This class implements a table that allows the user to sort the columns.
 * 
 * <p>This class is needed, because simply adding a {@link TableSorter} to a {@link JTable}
 * messes the selection mechanism of the {@link JTable}.
 * 
 * @author Sander Kooijmans
 */
public class JSortedTable extends JTable {

    /** The table model that sorts the rows. */
    private TableSorter tableSorter;
    
    /**
     * Constructs a table based on the specified table model.
     * @param tableModel the table model
     */
    public JSortedTable(TableModel tableModel) {
        super();
        tableSorter = new TableSorter(tableModel, super.getTableHeader());
        super.setModel(tableSorter);
    }
    
    public ListSelectionModel getUnsortedSelectionModel() {
        return new ListSelectionModelWrapper(super.getSelectionModel());
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
            for (int i=0; i<getRowCount(); i++) {
                if (wrappedModel.isSelectedIndex(i)) {
                    result = Math.max(result, tableSorter.modelIndex(i));
                }
            }
            return result;
        }

        public int getMinSelectionIndex() {
            int result = Integer.MAX_VALUE;
            for (int i=0; i<getRowCount(); i++) {
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
