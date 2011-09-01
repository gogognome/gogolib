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
package nl.gogognome.lib.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import nl.gogognome.lib.util.ComparatorUtil;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDriver;
import org.apache.commons.pool.impl.GenericObjectPool;

/**
 * This class creates connections to the database.
 *
 * @author Sander Kooijmans
 */
public class ConnectionPool {

	private final static String POOL_NAME = "service";

	private PoolingDriver driver;

	private String url;
	private String user;
	private String password;

	public ConnectionPool() {
	}

	/**
	 * Gets a database connection from the pool. The connection will have auto commit off
	 * and transaction level "read committed".
	 * @return the connection
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		ensureDriverInitialized();
		Connection connection = DriverManager.getConnection("jdbc:apache:commons:dbcp:" + POOL_NAME);
		initConnection(connection);
		return connection;
	}

	private void initConnection(Connection connection) throws SQLException {
		connection.setAutoCommit(false);
		connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
	}

	/**
	 * Releases a connection. The connection is returned to the pool.
	 * @param connection the connection. If null, then this method has no effect.
	 * @throws SQLException
	 */
	public void releaseConnection(Connection connection) throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}

	private synchronized void ensureDriverInitialized() throws SQLException {
		if (driver == null) {
			initPoolingDriver();
		}
	}

	private void initPoolingDriver() throws SQLException {
		registerHsqlDriver();

		if (url == null) {
			throw new SQLException("Connection parameters have not been set");
		}
		GenericObjectPool connectionPool = new GenericObjectPool(null);
		ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(url, user, password);
		PoolableConnectionFactory poolableConnectionFactory =
			new PoolableConnectionFactory(connectionFactory, connectionPool, null, null, false, false);
		driver = new PoolingDriver();
		driver.registerPool(POOL_NAME, connectionPool);
	}

	private void registerHsqlDriver() throws SQLException {
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
		} catch (Exception e) {
			throw new SQLException("Failed to initialize JDBC driver", e);
		}
	}

	public void setJdbcConnectionParameters(String url, String user, String password) throws SQLException {
		if (!ComparatorUtil.equals(url, this.url) || !ComparatorUtil.equals(user, this.user)
				|| !ComparatorUtil.equals(password, this.password)) {
			ensurePoolingDriverDeinitialized();

			this.url = url;
			this.user = user;
			this.password = password;
		}
	}

	private synchronized void ensurePoolingDriverDeinitialized() throws SQLException {
		if (driver != null) {
			deinitPoolingDriver();
		}

	}

	private void deinitPoolingDriver() throws SQLException {
		driver.closePool(POOL_NAME);
		driver = null;
	}
}
