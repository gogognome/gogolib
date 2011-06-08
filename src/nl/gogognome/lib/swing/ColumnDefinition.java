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

import java.util.Comparator;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import nl.gogognome.lib.text.TextResource;

/**
 * This class defines a column in a table.
 *
 * @author Sander Kooijmans
 */
public class ColumnDefinition {

    /** The identifier for the name of the column. */
    private String id;

    /** The class to which all values of the column belong. */
    private Class<?> classOfValues;

    /** The column width in pixels. */
    private int widthInPixels;

    /**
     * The table cell renderer for the column. <code>null</code> indicates that
     * the default table cell renderer must be used.
     */
    private TableCellRenderer tableCellRenderer;

    /**
     * The table cell editor for the column. <code>null</code> indicates that the
     * default table cell editor must be used.
     */
    private TableCellEditor tableCellEditor;

    /**
     * The comparator used to sort the values of this column.
     * <code>null</code> indicates that the default comparator must be used.
     */
    private Comparator<Object> comparator;

    /**
     * Constructor.
     * @param id the id of the column's name
     * @param classOfValues the class to which all values of the column belong
     * @param widthInPixels the column width in pixels
     */
    public ColumnDefinition(String id, Class<?> classOfValues,
            int widthInPixels) {
        super();
        this.id = id;
        this.classOfValues = classOfValues;
        this.widthInPixels = widthInPixels;
    }

    public String getName() {
        return TextResource.getInstance().getString(id);
    }

    public Class<?> getClassOfValues() {
        return classOfValues;
    }

    public int getWidthInPixels() {
        return widthInPixels;
    }

    public TableCellRenderer getTableCellRenderer() {
        return tableCellRenderer;
    }

    public TableCellEditor getTableCellEditor() {
		return tableCellEditor;
	}

    public Comparator<Object> getComparator() {
        return comparator;
    }

    /**
     * A builder for creating {@link ColumnDefinition}s.
     *
     * @author Sander Kooijmans
     */
    public static class Builder {

    	private ColumnDefinition columnDefinition;

        public Builder(String id, Class<?> classOfValues,
                int widthInPixels) {
        	columnDefinition = new ColumnDefinition(id, classOfValues, widthInPixels);
        }

        public Builder add(TableCellRenderer tableCellRenderer) {
        	columnDefinition.tableCellRenderer = tableCellRenderer;
        	return this;
        }

        public Builder add(TableCellEditor tableCellEditor) {
        	columnDefinition.tableCellEditor = tableCellEditor;
        	return this;
        }

        public Builder add(Comparator<Object> comparator) {
        	columnDefinition.comparator = comparator;
        	return this;
        }

        public ColumnDefinition build() {
        	return columnDefinition;
        }
    }
}
