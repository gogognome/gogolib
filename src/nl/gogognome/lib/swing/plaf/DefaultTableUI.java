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
package nl.gogognome.lib.swing.plaf;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.HashSet;

import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTableUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import nl.gogognome.lib.text.TextResource;

/**
 * The default look and feel for a table.
 */
public class DefaultTableUI extends BasicTableUI {

    public static ComponentUI createUI(JComponent c) {
        return new DefaultTableUI();
    }

    @Override
	public void installUI(JComponent c) {
        super.installUI(c);
        final JTable table = (JTable) c;

        table.setDefaultRenderer(Date.class, new DateRenderer());

        installAlternatingBackgroundRenderers(table);

        // Add listener that installs the alternating background renderers each time the model
        // of the table changes (not when the contents of the model change, but when a new model
        // is set in the table).
        table.addPropertyChangeListener("model", new PropertyChangeListener() {
            @Override
			public void propertyChange(PropertyChangeEvent evt) {
                installAlternatingBackgroundRenderers(table);
            }
        });
    }

    @Override
	protected void installKeyboardActions() {
        super.installKeyboardActions();
        InputMap inputMap = table.getInputMap();
        inputMap.remove(KeyStroke.getKeyStroke("ENTER"));
    }

    private static class DateRenderer extends DefaultTableCellRenderer {
        /**
         * @see javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
         */
        @Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            if (value instanceof Date) {
                value = TextResource.getInstance().formatDate("gen.dateFormat", (Date) value);
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    /**
     * Install alternating background renderer for the columns.
     * @param table the table for which the renderers must be installed
     */
    private static void installAlternatingBackgroundRenderers(JTable table) {
        HashSet<Class<?>> classes = new HashSet<Class<?>>();
        for (int col=0; col<table.getColumnCount(); col++) {
            Class clazz = table.getColumnClass(col);
            if (!classes.contains(clazz)) {
                classes.add(clazz);
                TableCellRenderer renderer = table.getDefaultRenderer(clazz);
                table.setDefaultRenderer(clazz, new AlternatingBackgroundRenderer(renderer));
            }

            table.getColumnModel().getColumn(col).addPropertyChangeListener(new PropertyChangeListener() {
                @Override
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
}
