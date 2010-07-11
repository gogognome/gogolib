/**
 *
 */
package nl.gogognome.swing;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelListener;

/**
 * Abstract class for {@link SortedTableModel}. The rows of the table model
 * are stored in a list. This class offers methods to modify the list.
 * Modifications of the list are signalled the registered {@link TableModelListener}s.
 * @param <T> the type of rows
 */
public abstract class AbstractListSortedTableModel<T> extends AbstractSortedTableModel {

	private List<T> rows;

    /**
     * Constructor.
     * @param columnDefinitions the column definitions
     * @param initialRows the initial rows
     */
    public AbstractListSortedTableModel(List<ColumnDefinition> columnDefinitions, List<T> initialRows) {
        super(columnDefinitions);
        rows = new ArrayList<T>(initialRows);
    }

	public int getRowCount() {
		return rows.size();
	}

	/**
	 * Adds a row to the table model.
	 * This method must be called from the AWT event thread.
	 * @param row the row
	 */
	public void addRow(T row) {
		rows.add(row);
		fireTableRowsInserted(rows.size() - 1, rows.size() - 1);
	}

	/**
	 * Removes a row from the table model.
	 * This method must be called from the AWT event thread.
	 * @param index the index of the row
	 */
	public void removeRow(int index) {
		rows.remove(index);
		fireTableRowsDeleted(index, index);
	}

	/**
	 * Updates a row.
	 * This method must be called from the AWT event thread.
	 * @param index the index of the row
	 * @param row the new value of the row
	 */
	public void updateRow(int index, T row) {
		rows.set(index, row);
		fireTableRowsUpdated(index, index);
	}

	/**
	 * Gets a row.
	 * @param index the index of the row
	 * @return the row
	 */
	public T getRow(int index) {
		return rows.get(index);
	}
}
