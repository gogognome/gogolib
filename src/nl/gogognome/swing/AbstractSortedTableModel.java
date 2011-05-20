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

    @Override
    public String getColumnName(int column) {
        return columnDefinitions.get(column).getName();
    }

    @Override
    public Class<?> getColumnClass(int column) {
        return columnDefinitions.get(column).getClassOfValues();
    }

    /** {@inheritDoc} */
    @Override
	public int getColumnWidth(int column) {
        return columnDefinitions.get(column).getWidthInPixels();
    }

    /** {@inheritDoc} */
    @Override
	public Comparator<Object> getComparator(int column) {
        return columnDefinitions.get(column).getComparator();
    }

    /** {@inheritDoc} */
    @Override
	public TableCellRenderer getRendererForColumn(int column) {
        return columnDefinitions.get(column).getTableCellRenderer();
    }

    /** {@inheritDoc} */
    @Override
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
