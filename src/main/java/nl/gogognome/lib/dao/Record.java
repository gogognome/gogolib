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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * This class represents a record from the table.
 *
 * @author Sander Kooijmans
 */
public class Record {

    private Table table;
    private ResultSet resultSet;

    public Record(Table table, ResultSet resultSet) {
        super();
        this.table = table;
        this.resultSet = resultSet;
    }

    public String getString(TableColumn column) throws SQLException {
        if (column.getType() != TableColumn.STRING) {
            throw new SQLException("Column " + table.getIndex(column) + " is not of type STRING.");
        }
        return resultSet.getString(column.getName());
    }

    public int getInt(TableColumn column) throws SQLException {
        if (column.getType() != TableColumn.INTEGER) {
            throw new SQLException("Column " + table.getIndex(column) + " is not of type INTEGER.");
        }

        return resultSet.getInt(column.getName());
    }

    public long getLong(TableColumn column) throws SQLException {
        if (column.getType() != TableColumn.LONG) {
            throw new SQLException("Column " + column.getName() + " is not of type LONG.");
        }

        return resultSet.getLong(column.getName());
    }

    public Date getDate(TableColumn column) throws SQLException {
        if (column.getType() != TableColumn.DATE) {
            throw new SQLException("Column " + column.getName() + " is not of type DATE.");
        }

        return resultSet.getDate(column.getName());
    }

    public double getDouble(TableColumn column) throws SQLException {
        if (column.getType() != TableColumn.DOUBLE) {
            throw new SQLException("Column " + column.getName() + " is not of type DOUBLE.");
        }

        return resultSet.getDouble(column.getName());
    }

    public boolean getBoolean(TableColumn column) throws SQLException {
        if (column.getType() != TableColumn.BOOLEAN) {
            throw new SQLException("Column " + column.getName() + " is not of type BOOLEAN.");
        }

        return resultSet.getBoolean(column.getName());
    }

    @Override
	public String toString() {
        return "Record[table: " + table + "; " + resultSet + ']';
    }
}
