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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;
import java.util.Iterator;

import nl.gogognome.lib.dbconnection.PreparedStatementWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for data access objects (DAOs). A DAO is responsible for
 * storing one type of objects in a database table and retrieving
 * objects from the same database table.
 *
 * @author Sander Kooijmans
 */
public class AbstractDAO {

	protected Logger logger = LoggerFactory.getLogger(getClass());

    protected final Connection connection;
    protected final Table table;

    /**
     * Constructor.
     * @param connection the connection to the database used by this DAO.
     * @param table the table on which this DAO operates.
     */
    protected AbstractDAO(Connection connection, Table table) {
        this.connection = connection;
        this.table = table;
    }

    public Table getTable() {
    	return table;
    }

	/**
	 * Creates a prepared statement. This method actually returns a {@link PreparedStatementWrapper},
	 * which has some advantages over using a regular {@link PreparedStatement}.
	 * @param query the query
	 * @return the prepared statement
	 * @throws SQLException if a problem occurs
	 */
	protected PreparedStatement prepareStatement(String query) throws SQLException {
		return PreparedStatementWrapper.preparedStatement(connection, query);
	}

    protected void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.warn("Ignored exception while closing statement: " +e.getMessage(), e);
            }
        }
    }

    protected void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.warn("Ignored exception while closing result set: " +e.getMessage(), e);
            }
        }
    }

    /**
     * Sets a parameter in a prepared statement.
     * @param statement the statement.
     * @param index the index of the parameter.
     * @param value the value to be set.
     * @throws SQLException if a problem occurs.
     */
    protected static void setParameter(PreparedStatement statement, int index, ColumnValuePair cvp) throws SQLException {
        if (cvp.getValue() == null) {
        	if (cvp.getColumn().isNotNull()) {
        		throw new SQLException("Cannot set null to column " + cvp.getName());
        	}
        	int sqlType;
	    	switch (cvp.getColumn().getType()) {
	        case TableColumn.BOOLEAN: sqlType = Types.BOOLEAN; break;
	        case TableColumn.DOUBLE: sqlType = Types.NUMERIC; break;
	        case TableColumn.DATE: sqlType = Types.DATE; break;
	        case TableColumn.INTEGER: sqlType = Types.INTEGER; break;
	        case TableColumn.LONG: sqlType = Types.BIGINT; break;
	        case TableColumn.STRING: sqlType = Types.VARCHAR; break;
	        default:
	            throw new SQLException("The type " + cvp.getColumn().getType() +  " is unsupported.");
	    	}
	        statement.setNull(index, sqlType);
        } else {
	    	switch (cvp.getColumn().getType()) {
	        case TableColumn.STRING:
	            statement.setString(index, (String) cvp.getValue());
	            break;

	        case TableColumn.INTEGER:
	            statement.setInt(index, ((Integer) cvp.getValue()).intValue());
	            break;

	        case TableColumn.LONG:
	            statement.setLong(index, ((Long) cvp.getValue()).longValue());
	            break;

	        case TableColumn.DATE:
	            statement.setDate(index, new java.sql.Date(((Date) cvp.getValue()).getTime()));
	            break;

	        case TableColumn.BOOLEAN:
	            statement.setBoolean(index, ((Boolean) cvp.getValue()).booleanValue());
	            break;

	        case TableColumn.DOUBLE:
	            statement.setDouble(index, ((Double) cvp.getValue()).doubleValue());
	            break;

	        default:
	            throw new SQLException("The type " + cvp.getColumn().getType() +  " is unsupported.");
	        }
        }
    }

    /**
     * Sets a parameter in a prepared statement.
     * @param statement the statement.
     * @param index the index of the parameter.
     * @param value the value to be set.
     * @throws SQLException if a problem occurs.
     */
    protected static void setParameter(PreparedStatement statement, int index, int value) throws SQLException {
        statement.setInt(index, value);
    }

    /**
     * Creates a where clause like "WHERE id=?" based on a list of {@link ColumnAndValue}s of the
     * primary key.
     * @param pkNamesAndValues the  {@link ColumnAndValue}s of the primary key.
     * @return the where clause
     */
    protected String createWhereClause(ColumnValuePairs pkNamesAndValues) {
        StringBuilder sb = new StringBuilder();
        sb.append("WHERE ");
        for (Iterator<ColumnValuePair> iter = pkNamesAndValues.iterator(); iter.hasNext(); ) {
        	ColumnValuePair c = iter.next();
            sb.append(c.getColumn().getName()).append("=?");
            if (iter.hasNext()) {
                sb.append(" AND ");
            }
        }
        return sb.toString();
    }

    /**
     * Sets parameter values for the {@link ColumnAndValue}s of the
     * primary key.
     * @param statement the prepared statement
     * @param paramIndex the index of the first parameter in the prepared statement to be filled in
     * @param pkNamesAndValues the  {@link ColumnAndValue}s of the primary key.
     * @throws SQLException if a problem occurs.
     */
    protected void setParameters(PreparedStatement statement, int paramIndex,
    		ColumnValuePairs pkNamesAndValues) throws SQLException {
        for (ColumnValuePair c : pkNamesAndValues) {
            setParameter(statement, paramIndex, c);
            paramIndex++;
        }
    }
}
