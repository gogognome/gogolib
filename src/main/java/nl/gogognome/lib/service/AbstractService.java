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
package nl.gogognome.lib.service;

import java.sql.Connection;
import java.sql.SQLException;

import nl.gogognome.lib.dbconnection.ConnectionPool;
import nl.gogognome.lib.util.Factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract class for services.
 *
 * @author Sander Kooijmans
 */
public class AbstractService {

    private Logger logger = LoggerFactory.getLogger(getClass());

	protected Connection connection;
	protected boolean success;
    private ConnectionPool connectionPool;

    /**
     * Constructor.
     */
    protected AbstractService() {
        connectionPool = Factory.getInstance(ConnectionPool.class);
    }

    /**
     * Gets a database connection. The database connection will have auto commit disabled.
     * @return the database connection
     * @throws SQLException if a problem occurs.
     */
    protected Connection createConnection() throws SQLException {
    	if (connection != null) {
    		throw new SQLException("No connection availble: a service method did not release its connection!");
    	}

    	success = false;
    	connection = connectionPool.getConnection();
    	connection.setAutoCommit(false);
        return connection;
    }

    /**
     * Commits changes to the database.
     */
    protected void commit() {
    	try {
			connection.commit();
		} catch (SQLException e) {
			logger.error("Problem occurred while committing changes:" + e.getMessage(), e);
		}
    }

    /**
     * Rolls back changes in the database.
     */
    protected void rollback() {
    	try {
    		if (connection != null) {
    			connection.rollback();
    		}
		} catch (SQLException e) {
			logger.error("Problem occurred while rolling back changes:" + e.getMessage(), e);
		}
    }

    /**
     * Commits or rolls back the transaction and then releases an {@link connection}.
     * @param commit <code>true</code> to commit the transaction before releasing the connection;
     *               <code>false</code> to roll back the transaction before releasing the connection
     */
    protected void releaseConnection(boolean commit) {
    	if (connection != null) {
    		try {
	        	if (commit) {
	        		commit();
	        	} else {
	        		rollback();
	        	}
    		} finally {
    			try {
					connection.close();
				} catch (SQLException e) {
					logger.warn("A pProblem occurred while closing the database connection.", e);
				}
    			connection = null;
    		}
    	}
    }

}
