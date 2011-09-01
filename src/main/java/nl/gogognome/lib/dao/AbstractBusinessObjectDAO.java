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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import nl.gogognome.lib.businessobject.BusinessObject;
import nl.gogognome.lib.businessobject.PrimaryKey;
import nl.gogognome.lib.util.Factory;


/**
 * Base class for data access objects (DAOs) that handle business objects.
 * A DAO is responsible for storing one type of business objects in
 * a database table and retrieving business objects from the same database table.
 *
 * @param <T> the type of the business objects
 */
public abstract class AbstractBusinessObjectDAO<P extends PrimaryKey, T extends BusinessObject<P>>
		extends AbstractDAO {

    protected final DBMSSpecificDAOPlugin plugin;

    /**
     * Constructor.
     * @param connection the connection to the database used by this DAO.
     * @param table the table on which this DAO operates.
     */
    protected AbstractBusinessObjectDAO(Connection connection, Table table) {
        super(connection, table);
        this.plugin = Factory.createInstance(DBMSSpecificDAOPlugin.class, connection, table);
    }

    /**
     * Finds a business object by its primary key.
     * @param pk the primary key.
     * @return the business object. Never <code>null</code>.
     * @throws SQLException if a problem occurs while searching for the BO.
     */
    public T findByPK(P pk) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
	        T result = null;
	        ColumnValuePairs pkNamesAndValues = getPKColumnsAndValues(pk);
	        statement = connection.prepareStatement("SELECT * FROM " + table.getName()
	        		+ ' ' + createWhereClause(pkNamesAndValues));
	        setParameters(statement, 1, pkNamesAndValues);
	        logger.debug("findByPk(): statement = " + statement.toString());
	        resultSet = statement.executeQuery();
	        while (resultSet.next()) {
	            if (result != null) {
	                throw new SQLException("Found more than 1 record with PK " + pk.getPresentationName());
	            }
	            result = convert(new Record(table, resultSet));
	        }

	        return result;
        } finally {
            closeStatement(statement);
            closeResultSet(resultSet);
        }
    }

    /**
     * Controleert of een record bestaat met het opgegeven PK.
     * @param pk de PK
     * @return <code>true</code> als er precies 1 record bestaat; anders <code>false</code>
     * @throws SQLException als er iets fout gaat tijdens het zoeken.
     */
    protected boolean existsBO(P pk) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            ColumnValuePairs pkNamesAndValues = getPKColumnsAndValues(pk);
            statement = connection.prepareStatement("SELECT * FROM " + table.getName() + ' '
            		+ createWhereClause(pkNamesAndValues));
            setParameters(statement, 1, pkNamesAndValues);
            logger.debug("exists(): statement = " + statement.toString());
            resultSet = statement.executeQuery();
            int count = 0;
            while (resultSet.next()) {
                count += 1;
                if (count > 1) {
                    throw new SQLException("Meer dan 1 resultaat gevonden met de PK " + pk.getPresentationName());
                }
            }

            return count == 1;
        } catch (SQLException e) {
            throw new SQLException("Probleem opgetreden bij het zoeken met de PK " + pk.getPresentationName() + ".", e);
        } finally {
            closeStatement(statement);
            closeResultSet(resultSet);
        }
    }

    /**
     * Helper method to search for exactly one object.
     * @param sqlQuery the SQL query
     * @param value the value for the first (and sole) parameter to be filled in to the query.
     * @return the object.
     * @throws Exception if a problem occurs while obtaining or converting the result set.
     */
    protected T findExactlyOne(String sqlQuery, Object value) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            T result = null;
            statement = connection.prepareStatement(sqlQuery);
            if (value instanceof String) {
                statement.setString(1, (String) value);
            } else if (value instanceof Integer) {
                statement.setInt(1, ((Integer) value).intValue());
            } else {
                throw new Exception("Waarde van het type " + value.getClass() +  " wordt niet ondersteund.");
            }
            logger.debug("findExactlyOne(): statement = " + statement.toString());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (result != null) {
                    throw new Exception("Meer dan 1 resultaat gevonden met de waarde " + value);
                }
                result = convert(new Record(table, resultSet));
            }

            return result;
        } catch (SQLException e) {
            throw new Exception("Probleem opgetreden bij het zoeken met de waarde " + value + ".", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }
    }

    /**
     * Gets a list of all business objects.
     * @return the list of all business objects
     * @throws Exception if a problem occurs reading a business object
     */
    protected List<T> findAllBOs() throws SQLException {
        return findAllBOs(null);
    }

    /**
     * Gets a list of all business objects.
     * @param orderBy an optional order-by clause. Can be <code>null</code>.
     * @return the list of all business objects
     * @throws SQLException if a problem occurs reading a business object
     */
    protected List<T> findAllBOs(String orderBy) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            List<T> result = new LinkedList<T>();
            Connection con = connection;
            String query = "SELECT * FROM " + table.getName();
            if (orderBy != null) {
                query += ' ' + orderBy;
            }
            statement = con.prepareStatement(query);
            logger.debug("findAll(): statement = " + statement.toString());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(convert(new Record(table, resultSet)));
            }

            return result;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }
    }

    /**
     * Gets a list of {@link BusinessObject}s matching a list of columns and values.
     * @param columnsAndValues the columns  and values
     * @return the list of all business objects
     * @throws Exception if a problem occurs while creating the list.
     */
    protected List<T> findByWhereClause(ColumnValuePairs columnsAndValues) throws SQLException {
        return findByWhereClause(columnsAndValues, null);
    }

    /**
     * Gets a list of {@link BusinessObject}s matching a list of column values.
     * @param columnNamesAndValues the column names and values
     * @param orderBy an optional order-by clause. <code>null</code> if no order-by clause
     *        is to be used.
     * @return the list of all business objects
     * @throws Exception if a problem occurs while creating the list.
     */
    protected List<T> findByWhereClause(ColumnValuePairs columnNamesAndValues, String orderBy) throws SQLException {
        PreparedStatement statement = null;
        String whereClause = null;
        whereClause = "SELECT * FROM " + table.getName() + ' ' + createWhereClause(columnNamesAndValues);
        if (orderBy != null) {
            whereClause += ' ' + orderBy;
        }
        statement = connection.prepareStatement(whereClause);
        setParameters(statement, 1, columnNamesAndValues);
        return executeSelectStatement(statement);
    }

    /**
     * Gets a list of business objects that are found by a prepared statement.
     * The statement will be closed by this method.
     * @param statement the prepared statement
     * @return the list of all business objects
     * @throws Exception if a problem occurs while executing the statemet and
     *         creating the list
     */
    protected List<T> executeSelectStatement(PreparedStatement statement) throws SQLException {
        List<T> result = new ArrayList<T>();
        ResultSet resultSet = null;
        logger.debug("executeSelectStatement(): statement = " + statement.toString());
        try {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(convert(new Record(table, resultSet)));
            }

            return result;
        } finally {
            closeStatement(statement);
            closeResultSet(resultSet);
        }
    }

    /**
     * Creates a business object in the database.
     * @param bo the business object.
     * @return the created business object. The difference with <code>bo</code>
     *         is that if <code>bo</code> has no PK then the returned business object will have one.
     * @throws SQLException if a problem occurs while creating the business object.
     */
    public T createBO(T bo) throws SQLException {
        T result = bo;
        PreparedStatement statement = null;
        ResultSet keysResultSet = null;
        try {
            Connection con = connection;
            StringBuilder query = new StringBuilder(200);
            query.append("INSERT INTO ").append(table.getName()).append(" (");
            ColumnValuePairs colValues = convert(bo);
            for (Iterator<ColumnValuePair> iter = colValues.iterator(); iter.hasNext(); ) {
                ColumnValuePair ColumnValuePair = iter.next();
                query.append(ColumnValuePair.getColumn().getName());
                if (iter.hasNext()) {
                    query.append(", ");
                }
            }
            query.append(") VALUES (");
            for (Iterator<ColumnValuePair> iter = colValues.iterator(); iter.hasNext(); ) {
            	ColumnValuePair cvp = iter.next();
				if (cvp.getValue() instanceof Literal) {
					query.append(((Literal)cvp.getValue()).getValue());
				} else {
					query.append('?');
				}
                if (iter.hasNext()) {
                    query.append(", ");
                }
            }
            query.append(')');
            statement = con.prepareStatement(query.toString());
            int index = 1;
            for (ColumnValuePair cvp : colValues) {
            	if (!(cvp.getValue() instanceof Literal)) {
            		setParameter(statement, index, cvp);
                    index++;
            	}
            }
            logger.debug("createBO(): statement = " + statement.toString());
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new SQLException("Number of rows created: " + count + "; bo: " + bo);
            }

            // If values are automatically created, then the BO can be updated with these values.
            keysResultSet = statement.getGeneratedKeys();
            if (keysResultSet.next()) {
                result = updateCreatedBO(bo, new Record(table, keysResultSet));
            }
            return result;
        } finally {
        	closeStatement(statement);
        	closeResultSet(keysResultSet);
        }
    }

    /**
     * Override this method to update a business object after it has been created.
     * This can be used to fill in automatically created values.
     * @param bo the business object
     * @param record the record containing the automatically generated values only
     * @return the updated business object
     * @throws SQLException if a problem occurs while updating the business object
     */
    protected T updateCreatedBO(T bo, Record record) throws SQLException {
        return bo;
    }

    /**
     * Updates a business object in the database.
     * @param bo the BO.
     * @throws Exception if a problem occurs while updating the BO.
     */
    protected void updateBO(T bo) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            Connection con = connection;
            StringBuilder query = new StringBuilder(200);
            query.append("UPDATE ").append(table.getName()).append(" SET ");
            ColumnValuePairs colValues = convert(bo);
            for (Iterator<ColumnValuePair> iter = colValues.iterator(); iter.hasNext(); ) {
                ColumnValuePair ColumnValuePair = iter.next();
                query.append(ColumnValuePair.getColumn().getName()).append("=?");
                if (iter.hasNext()) {
                    query.append(", ");
                }
            }
            ColumnValuePairs pkNamesAndValues = getPKColumnsAndValues(bo.getPK());
            query.append(' ').append(createWhereClause(pkNamesAndValues));
            statement = con.prepareStatement(query.toString());
            int index = 1;
            for (ColumnValuePair ColumnValuePair : colValues) {
                setParameter(statement, index, ColumnValuePair);
                index++;
            }
            setParameters(statement, index, pkNamesAndValues);
            logger.debug("updateBO(): statement = " + statement.toString());
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new SQLException(count + " rows updated instead of 1 row for business object: " + bo);
            }
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }
    }

    /**
     * Deletes a business object.
     * @param pk the PK of the business object.
     * @throws SQLException if a problem occurs while deleting the BO.
     */
    protected void deleteBO(P pk) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            ColumnValuePairs pkNamesAndValues = getPKColumnsAndValues(pk);
            statement = connection.prepareStatement("DELETE FROM " + table.getName() + ' ' + createWhereClause(pkNamesAndValues));
            setParameters(statement, 1, pkNamesAndValues);
            logger.debug("delete(): statement = " + statement.toString());
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new SQLException("Aantal regels verwijderd: " + count + "; pk: " + pk.getPresentationName());
            }
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }
    }

    /**
     * Gets the list of columns and values for the PK for a business object.
     * @param bo the business object.
     * @return the value.
     * @throws SQLException if a problem occurs while creating the list of coluns and values.
     */
    public abstract ColumnValuePairs getPKColumnsAndValues(P pk) throws SQLException;

    /**
     * Converts a record from the table to a business object.
     * @param record a record from the table
     * @return the business object
     * @throws Exception if a problem occurs while converting the record.
     */
    public abstract T convert(Record record) throws SQLException;

    /**
     * Converts a business object into a list of {@link ColumnValuePair}s.
     * @param bo the BO.
     * @return a list of {@link ColumnValuePair}s.
     * @throws Exception if a problem occurs while converting.
     */
    public abstract ColumnValuePairs convert(T bo) throws SQLException;

	public void createTable() throws SQLException {
		plugin.createTable();
	}

	public void dropTable() throws SQLException {
		plugin.dropTable();
	}
}
