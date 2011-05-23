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
     * The comparator used to sort the values of this column.
     * <code>null</code> indicates that the default comparator must be used.
     */
    private Comparator<Object> comparator;


    /**
     * Constructor.
     * @param id the id of the column's name
     * @param classOfValues the class to which all values of the column belong
     * @param widthInPixels the column width in pixels
     * @param tableCellRenderer the table cell renderer for the column. <code>null</code> indicates that
     *                          the default table cell renderer must be used.
     * @param comparator the comparator used to sort the values of this column.
     *                   <code>null</code> indicates that the default comparator must be used.
     */
    public ColumnDefinition(String id, Class<?> classOfValues,
            int widthInPixels, TableCellRenderer tableCellRenderer,
            Comparator<Object> comparator) {
        super();
        this.id = id;
        this.classOfValues = classOfValues;
        this.widthInPixels = widthInPixels;
        this.tableCellRenderer = tableCellRenderer;
        this.comparator = comparator;
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

    public Comparator<Object> getComparator() {
        return comparator;
    }
}
