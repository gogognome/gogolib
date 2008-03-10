package nl.gogognome.swing.plaf;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * This class wraps a {@link TableCellRenderer}. It uses the wrapped renderer to obtain
 * a table cell renderer component and sets the background color of that component alternatingly.
 */
public class AlternatingBackgroundRenderer implements TableCellRenderer {

    /** Background color for the odd rows. */
    private final static Color COLOR_ODD_ROWS = new Color(240, 240, 255);

    /** The wrapped renderer. */
    private TableCellRenderer wrappedRenderer;

    /**
     * Constructor.
     * @param wrappedRenderer the wrapped renderer
     */
    public AlternatingBackgroundRenderer(TableCellRenderer wrappedRenderer) {
        this.wrappedRenderer = wrappedRenderer;
    }
    
    /**
     * @see TableCellRenderer#getTableCellRendererComponent(JTable, Object, boolean, boolean, int, int)
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = wrappedRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (!isSelected) {
            component.setBackground(row % 2 == 0 ? Color.WHITE : COLOR_ODD_ROWS);
        }
        return component;
    }
    
}