/*
 * $Id: AbstractSortedTableModel.java,v 1.1 2009-12-01 19:26:30 sanderk Exp $
 */

package nl.gogognome.swing;

import java.util.Comparator;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

/**
 * Abstract class for {@link SortedTableModel} implementations.
 *
 * @author Sander Kooijmans
 */
public abstract class AbstractSortedTableModel extends AbstractTableModel implements SortedTableModel {

    /** The column definitions. */
    private List<ColumnDefinition> columnDefinitions;

    /**
     * Constructor.
     * @param columnDefinitions the column definitions
     */
    public AbstractSortedTableModel(List<ColumnDefinition> columnDefinitions) {
        super();
        this.columnDefinitions = columnDefinitions;
    }

    /** {@inheritDoc} */
    public int getColumnWidth(int column) {
        return columnDefinitions.get(column).getWidthInPixels();
    }

    /** {@inheritDoc} */
    public Comparator<Object> getComparator(int column) {
        return columnDefinitions.get(column).getComparator();
    }

    /** {@inheritDoc} */
    public TableCellRenderer getRendererForColumn(int column) {
        return columnDefinitions.get(column).getTableCellRenderer();
    }

    /** {@inheritDoc} */
    public int getColumnCount() {
        return columnDefinitions.size();
    }

    /**
     * Gets the column definition for the specified column.
     * @param column the index of the column
     * @return the column definition
     */
    public ColumnDefinition getColumnDefinition(int column) {
        return columnDefinitions.get(column);
    }
}
