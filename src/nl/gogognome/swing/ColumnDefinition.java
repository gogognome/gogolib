/*
 * $Id: ColumnDefinition.java,v 1.1 2009-12-01 19:26:30 sanderk Exp $
 */

package nl.gogognome.swing;

import java.util.Comparator;

import javax.swing.table.TableCellRenderer;

import nl.gogognome.text.TextResource;

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
