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
package nl.gogognome.lib.test.database;

import java.util.Arrays;
import java.util.List;

import nl.gogognome.lib.dao.Table;
import nl.gogognome.lib.dao.TableColumn;

/**
 * A table used in the database test.
 *
 * @author Sander Kooijmans
 */
public class TestTable extends Table {

	public final static TableColumn ID = TableColumn.createIntegerColumn("ID", 10, true);
	public final static TableColumn INT = TableColumn.createIntegerColumn("INT", 5, false);
	public final static TableColumn BOOL = TableColumn.createBooleanColumn("BOOL");
	public final static TableColumn DATE = TableColumn.createDateColumn("DATE", false);
	public final static TableColumn LONG = TableColumn.createLongColumn("LONG", 20, false);
	public final static TableColumn STRING_NOT_NULL =
			TableColumn.createStringColumn("STRING", 10, true);

	private final static List<TableColumn> COLUMNS = Arrays.asList(
			ID, INT, BOOL, DATE, LONG, STRING_NOT_NULL);

	public TestTable() {
		super("test", COLUMNS, ID);
	}
}
