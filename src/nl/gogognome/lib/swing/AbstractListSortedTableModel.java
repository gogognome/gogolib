/*
   Copyright 2011 Sander Kooijmans

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package nl.gogognome.lib.swing;

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

	@Override
	public int getRowCount() {
		return rows.size();
	}

	/**
	 * Adds a row to the table model.
	 * Notifies all listeners about the change in the table.
	 * This method must be called from the AWT event thread.
	 * @param row the row
	 */
	public void addRow(T row) {
		rows.add(row);
		fireTableRowsInserted(rows.size() - 1, rows.size() - 1);
	}

	/**
	 * Removes a row from the table model.
	 * Notifies all listeners about the change in the table.
	 * This method must be called from the AWT event thread.
	 * @param index the index of the row
	 */
	public void removeRow(int index) {
		rows.remove(index);
		fireTableRowsDeleted(index, index);
	}

	/**
	 * Removes all rows from the table model.
	 * Notifies all listeners about the change in the table.
	 * This method must be called from the AWT event thread.
	 */
	public void clear() {
		int oldSize = rows.size();
		rows.clear();
		fireTableRowsDeleted(0, oldSize);
	}

	/**
	 * Updates a row.
	 * Notifies all listeners about the change in the table.
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
