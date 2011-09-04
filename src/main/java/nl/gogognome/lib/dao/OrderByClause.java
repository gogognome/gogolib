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

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an "order by" clause for an SQL query.
 */
public class OrderByClause {

	public static class TableColumnAndDirection {
		private final TableColumn column;
		private final boolean ascending;

		public TableColumnAndDirection(TableColumn column, boolean ascending) {
			this.column = column;
			this.ascending = ascending;
		}

		public TableColumn getColumn() {
			return column;
		}

		public boolean isAscending() {
			return ascending;
		}

		public boolean isDescending() {
			return !isAscending();
		}
	}

	private List<TableColumnAndDirection> columnsAndDirections = new ArrayList<TableColumnAndDirection>();

	public OrderByClause() {
	}

	public static OrderByClause ascending(TableColumn column) {
		OrderByClause orderByClause = new OrderByClause();
		orderByClause.addAscending(column);
		return orderByClause;
	}

	public void addAscending(TableColumn column) {
		columnsAndDirections.add(new TableColumnAndDirection(column, true));
	}

	public void addDescending(TableColumn column) {
		columnsAndDirections.add(new TableColumnAndDirection(column, false));
	}

	public List<TableColumnAndDirection> getColumnAndDirections() {
		return columnsAndDirections;
	}
}
