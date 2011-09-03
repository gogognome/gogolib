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
package nl.gogognome.lib.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nl.gogognome.lib.businessobject.IntegerPrimaryKey;

/**
 * This class represents a list of column-value pairs.
 *
 * @author Sander Kooijmans
 */
public class ColumnValuePairs implements Iterable<ColumnValuePair> {
	private final List<ColumnValuePair> columnValuePairs = new ArrayList<ColumnValuePair>();

	public ColumnValuePairs() {
	}

	/**
	 * Convenience method to create a {@link ColumnValuePairs} instance containing
	 * one column value pair.
	 * @param column the column
	 * @param value the value
	 * @return the {@link ColumnValuePairs} instance
	 * @throws SQLException
	 */
	public static ColumnValuePairs of(TableColumn column, IntegerPrimaryKey value) {
		ColumnValuePairs nvp = new ColumnValuePairs();
		nvp.add(column, value);
		return nvp;
	}

	/**
	 * Convenience method to create a {@link ColumnValuePairs} instance containing
	 * one column value pair.
	 * @param column the column
	 * @param value the value
	 * @return the {@link ColumnValuePairs} instance
	 * @throws SQLException
	 */
	public static ColumnValuePairs of(TableColumn column, Object value) {
		ColumnValuePairs nvp = new ColumnValuePairs();
		nvp.add(column, value);
		return nvp;
	}

	public void add(TableColumn column, Object value) {
		columnValuePairs.add(new ColumnValuePair(column, value));
	}

	public void add(TableColumn column, IntegerPrimaryKey value) {
		if (value != null) {
			add(column, value.getId());
		} else {
			columnValuePairs.add(new ColumnValuePair(column, null));
		}
	}

	public void addLiteral(TableColumn column, String value) {
		columnValuePairs.add(new ColumnValuePair(column, new Literal(value)));
	}

	/**
	 * Gets the {@link ColumnValuePair} with the specified column.
	 * @param column the column
	 * @return the {@link ColumnValuePair} or <code>null</code> if no {@link ColumnValuePair}
	 *         was specified for the column.
	 */
	public ColumnValuePair getColumnValuePair(TableColumn column) {
		for (ColumnValuePair nvp : columnValuePairs) {
			if (nvp.getColumn().equals(column)) {
				return nvp;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(1000);
		for (int i=0; i<columnValuePairs.size(); i++) {
			sb.append(columnValuePairs.get(i));
			if (i+1 < columnValuePairs.size()) {
				sb.append(',');
			}
		}
		return sb.toString();
	}

	@Override
	public Iterator<ColumnValuePair> iterator() {
		return columnValuePairs.iterator();
	}

	public int size() {
		return columnValuePairs.size();
	}

	public void remove(TableColumn column) {
		for (Iterator<ColumnValuePair> iter = columnValuePairs.iterator(); iter.hasNext(); ) {
			ColumnValuePair nvp = iter.next();
			if (nvp.getColumn().equals(column)) {
				iter.remove();
			}
		}

	}

	public boolean isEmpty() {
		return columnValuePairs.isEmpty();
	}

	public Object getValue(TableColumn column) throws SQLException {
		ColumnValuePair cvp = getColumnValuePair(column);
		if (cvp == null) {
			throw new SQLException("No value set for column " + column);
		}
		return cvp.getValue();
	}

	public int getIntValue(TableColumn column) throws SQLException {
		if (column.getType() != TableColumn.INTEGER) {
			throw new SQLException("Cannot get integer value for column with type " + column.getType());
		}

		Object o = getValue(column);
		if (o == null) {
			throw new SQLException("Column " + column + " is null");
		}
		return ((Integer)o).intValue();
	}


}