/*
    This file is part of gogolib.

    gogolib is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    gogolib is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with gogolib.  If not, see <http://www.gnu.org/licenses/>.
*/
package nl.gogognome.lib.swing;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import nl.gogognome.lib.swing.plaf.AlternatingBackgroundRenderer;

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

    /** The scroll pane that contains the table. */
    private JScrollPane scrollPane;

    /**
     * The selection model. The row numbers in the selection model refer to the unsorted rows.
     */
    private ListSelectionModel selectionModel;

    /** If <code>true</code> then the user cannot change the way the table is sorted. */
    private boolean disableSorting;

    /**
     * Constructs a table based on the specified table model. The table allows selection of rows,
     * not of columns.
     * @param tableModel the table model
     * @param disableSorting <code>true</code> if the user cannot change the sorting;
     *        <code>false</code> if the user can change the sorting.
     * @param autoResizeMode the auto resize mode of the table
     */
    public SortedTableImpl(SortedTableModel tableModel, boolean disableSorting, int autoResizeMode) {
        super();
        this.disableSorting = disableSorting;

        table = new JTable();
        tableSorter = new TableSorter(tableModel, disableSorting ? null : table.getTableHeader());
        table.setModel(tableSorter);
        selectionModel = new ListSelectionModelWrapper(table.getSelectionModel());

        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);
        table.setAutoResizeMode(autoResizeMode);

        TableColumnModel columnModel = table.getColumnModel();

        int width = 0;
        int nrCols = tableModel.getColumnCount();
        for (int i=0; i<nrCols; i++) {
            // Set column width
            int colWidth = tableModel.getColumnWidth(i);
            TableColumn column = columnModel.getColumn(i);
            column.setPreferredWidth(colWidth);
            width += colWidth;

            // If present, set the table cell renderer
            TableCellRenderer renderer = tableModel.getRendererForColumn(i);
            if (renderer != null) {
                column.setCellRenderer(new AlternatingBackgroundRenderer(renderer));
            }
        }

        scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(false);
    }

    /**
     * @see SortedTable#getComponent()
     */
    @Override
	public JComponent getComponent() {
        return scrollPane;
    }

    /**
     * @see SortedTable#getSelectionModel()
     */
    @Override
	public ListSelectionModel getSelectionModel() {
        return selectionModel;
    }

    /**
     * @see SortedTable#setTitle(String)
     */
    @Override
    public void setTitle(String title) {
        scrollPane.setBorder(WidgetFactory.getInstance().createTitleBorder(title));
    }

    private class ListSelectionModelWrapper implements ListSelectionModel {

        private ListSelectionModel wrappedModel;

        public ListSelectionModelWrapper(ListSelectionModel wrappedModel) {
            this.wrappedModel = wrappedModel;
        }

        @Override
		public void addListSelectionListener(ListSelectionListener listener) {
            wrappedModel.addListSelectionListener(listener);
        }

        @Override
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

        @Override
		public void clearSelection() {
            wrappedModel.clearSelection();
        }

        @Override
		public int getAnchorSelectionIndex() {
            return tableSorter.modelIndex(wrappedModel.getAnchorSelectionIndex());
        }

        @Override
		public int getLeadSelectionIndex() {
            return tableSorter.modelIndex(wrappedModel.getLeadSelectionIndex());
        }

        @Override
		public int getMaxSelectionIndex() {
            int result = -1;
            for (int i=0; i<table.getRowCount(); i++) {
                if (wrappedModel.isSelectedIndex(i)) {
                    result = Math.max(result, tableSorter.modelIndex(i));
                }
            }
            return result;
        }

        @Override
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

        @Override
		public int getSelectionMode() {
            return wrappedModel.getSelectionMode();
        }

        @Override
		public boolean getValueIsAdjusting() {
            return wrappedModel.getValueIsAdjusting();
        }

        @Override
		public void insertIndexInterval(int index, int length, boolean before) {
            // TODO Auto-generated method stub
            System.out.println("insertIndexInterval(" + index + ", " + length + ", " + before);
        }

        @Override
		public boolean isSelectedIndex(int index) {
            return wrappedModel.isSelectedIndex(tableSorter.viewIndex(index));
        }

        @Override
		public boolean isSelectionEmpty() {
            return wrappedModel.isSelectionEmpty();
        }

        @Override
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

        @Override
		public void removeListSelectionListener(ListSelectionListener listener) {
            wrappedModel.removeListSelectionListener(listener);
        }

        @Override
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

        @Override
		public void setAnchorSelectionIndex(int index) {
            wrappedModel.setAnchorSelectionIndex(tableSorter.viewIndex(index));
        }

        @Override
		public void setLeadSelectionIndex(int index) {
            wrappedModel.setLeadSelectionIndex(tableSorter.viewIndex(index));
        }

        @Override
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

        @Override
		public void setSelectionMode(int selectionMode) {
            wrappedModel.setSelectionMode(selectionMode);
        }

        @Override
		public void setValueIsAdjusting(boolean valueIsAdjusting) {
            wrappedModel.setValueIsAdjusting(valueIsAdjusting);
        }

    }

    /**
     * @see SortedTable#getFocusableComponent()
     */
    @Override
	public Component getFocusableComponent() {
        return table;
    }

    /**
     * @see SortedTable#selectFirstRow()
     */
    @Override
	public void selectFirstRow() {
        if (tableSorter.getRowCount() > 0) {
            table.getSelectionModel().clearSelection();
            table.getSelectionModel().setSelectionInterval(0, 0);
        }
    }

    /**
     * @see nl.gogognome.lib.swing.SortedTable#getSelectedRows()
     */
    @Override
	public int[] getSelectedRows() {
        int[] rows = table.getSelectedRows();
        // Convert view rows to model rows
        for (int i=0; i<rows.length; i++) {
            rows[i] = tableSorter.modelIndex(rows[i]);
        }
        return rows;
    }

    /**
     * @see nl.gogognome.lib.swing.SortedTable#setSelectionAction(javax.swing.Action)
     */
    @Override
	public void setSelectionAction(final Action action) {
        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    action.actionPerformed(null);
                }
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    action.actionPerformed(null);
                }
            }
        });
    }

    /**
     * @see nl.gogognome.lib.swing.SortedTable#setSortingStatus(int, int)
     */
    @Override
	public void setSortingStatus(int column, int status) {
        tableSorter.setSortingStatus(column, status);
    }
}
