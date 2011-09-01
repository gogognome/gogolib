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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * This class defines a table.
 *
 * @author Sander Kooijmans
 */
public class Table {

    private final String name;
    private List<TableColumn> columns;
    private List<TableColumn> primaryKeyColumns;

    public Table(String name, TableColumn[] columns, TableColumn primaryKeyColumn) {
        this(name, Arrays.asList(columns), primaryKeyColumn);
    }

    public Table(String name, TableColumn[] columns, TableColumn[] primaryKeyColumns) {
        this(name, Arrays.asList(columns), Arrays.asList(primaryKeyColumns));
    }

    public Table(String name, List<TableColumn> columns, TableColumn primaryKeyColumn) {
        super();
        this.name = name;
        this.columns = columns;
        this.primaryKeyColumns = Arrays.asList(primaryKeyColumn);
    }

    public Table(String name, List<TableColumn> columns, List<TableColumn> primaryKeyColumns) {
        super();
        this.name = name;
        this.columns = Collections.unmodifiableList(columns);
        this.primaryKeyColumns = Collections.unmodifiableList(primaryKeyColumns);
    }

    public String getName() {
        return name;
    }

    public List<TableColumn> getColumns() {
        return columns;
    }

    public List<TableColumn> getPrimaryKeyColumns() {
        return primaryKeyColumns;
    }

    @Override
	public boolean equals(Object obj) {
        if (obj instanceof Table) {
            Table that = (Table) obj;
            return this.getName().equals(that.getName());
        } else {
            return false;
        }
    }

    @Override
	public int hashCode() {
        return getName().hashCode();
    }

    /**
     * Gets the index of the specified column in the table.
     * @param column the column
     * @return the index
     */
    public int getIndex(TableColumn column) {
        return getColumns().indexOf(column) + 1; // SQL is 1 based instead of zero-based.
    }

    /**
     * Gets the set of tables on which this table depends. Dependency refers directly or indirectly
     * to these tables using foreign keys.
     * @return the set of tables on which this table depends.
     */
    public Set<Table> getDependentTables() {
        Set<Table> set = new HashSet<Table>();
        Queue<Table> tablesToBeChecked = new LinkedList<Table>();
        tablesToBeChecked.add(this);
        while (!tablesToBeChecked.isEmpty()) {
            Table curTable = tablesToBeChecked.remove();
            for (TableColumn column  : curTable.getColumns()) {
                if (column.isForeignKey()) {
                    Table dependentTable = column.getForeignKeyTable();
                    if (!set.contains(dependentTable) && !dependentTable.equals(this)) {
                        set.add(dependentTable);
                        tablesToBeChecked.add(dependentTable);
                    }
                }
            }
        }
        return set;
    }
}
