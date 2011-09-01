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

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class wraps a PreparedStatement. This class has two advantages over using
 * the PreparedStatement directly:
 *
 * 1. Its toString() method returns the query that was used to create the statement
 * 2. Its finalizer logs a message if the statement was not closed
 */
public class PreparedStatementWrapper implements PreparedStatement {

	private final static Logger LOGGER = LoggerFactory.getLogger(PreparedStatementWrapper.class);

	private final PreparedStatement wrappedStatement;
	private final String query;
	private boolean closed;
	private long startTimeNano;

	private List<Object> parameters = new ArrayList<Object>(10);

	/**
	 * Constructor.
	 * @param wrapepdStatement the wrapped statement
	 * @param query the query used to create the wrapped statement. Used for logging only.
	 * @param startTimeNano the creation time (in nanoseconds) of the prepared statement
	 */
	private PreparedStatementWrapper(PreparedStatement wrapepdStatement,
			String query, long startTimeNano) {
		super();
		this.wrappedStatement = wrapepdStatement;
		this.query = query;
		this.startTimeNano = startTimeNano;
	}

	/**
	 * Creates a prepared statement
	 * @param connection the connection
	 * @param query the query
	 * @return the prepared statement
	 * @throws SQLException if a problem occurs
	 */
	public static PreparedStatement preparedStatement(Connection connection, String query) throws SQLException {
		long startTimeNao = System.nanoTime();
		PreparedStatement wrappedStatement = connection.prepareStatement(query);
		return new PreparedStatementWrapper(wrappedStatement, query, startTimeNao);
	}

	@Override
	public void addBatch() throws SQLException {
		wrappedStatement.addBatch();
	}

	@Override
	public void clearParameters() throws SQLException {
		wrappedStatement.clearParameters();
	}

	@Override
	public boolean execute() throws SQLException {
		try {
			return wrappedStatement.execute();
		} catch (SQLException e) {
			// Throw the same exception but with the message extended with the query.
			throw new SQLException(e.getMessage() + " in query " + toString(),
					e.getSQLState(), e.getErrorCode(), e);
		} finally {
			long endTimeNano = System.nanoTime();
			logStatement(startTimeNano, endTimeNano);
		}
	}

	@Override
	public ResultSet executeQuery() throws SQLException {
		try {
			return wrappedStatement.executeQuery();
		} catch (SQLException e) {
			// Throw the same exception but with the message extended with the query.
			throw new SQLException(e.getMessage() + " in query " + toString(),
					e.getSQLState(), e.getErrorCode(), e);
		} finally {
			long endTimeNano = System.nanoTime();
			logStatement(startTimeNano, endTimeNano);
		}
	}

	@Override
	public int executeUpdate() throws SQLException {
		try {
			return wrappedStatement.executeUpdate();
		} catch (SQLException e) {
			// Throw the same exception but with the message extended with the query.
			throw new SQLException(e.getMessage() + " in query " + toString(),
					e.getSQLState(), e.getErrorCode(), e);
		} finally {
			long endTimeNano = System.nanoTime();
			logStatement(startTimeNano, endTimeNano);
		}
	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		return wrappedStatement.getMetaData();
	}

	@Override
	public ParameterMetaData getParameterMetaData() throws SQLException {
		return wrappedStatement.getParameterMetaData();
	}

	@Override
	public void setArray(int parameterIndex, Array x) throws SQLException {
		wrappedStatement.setArray(parameterIndex, x);
	}

	@Override
	public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
		wrappedStatement.setAsciiStream(parameterIndex, x);
	}

	@Override
	public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
		wrappedStatement.setAsciiStream(parameterIndex, x, length);
	}

	@Override
	public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
		wrappedStatement.setAsciiStream(parameterIndex, x, length);
	}

	@Override
	public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
		wrappedStatement.setBigDecimal(parameterIndex, x);
	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
		wrappedStatement.setBinaryStream(parameterIndex, x);
	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
		wrappedStatement.setBinaryStream(parameterIndex, x, length);
	}


	@Override
	public void setBinaryStream(int parameterIndex, InputStream x, long length)
			throws SQLException {
		wrappedStatement.setBinaryStream(parameterIndex, x, length);
	}

	@Override
	public void setBlob(int parameterIndex, Blob x) throws SQLException {
		wrappedStatement.setBlob(parameterIndex, x);
	}

	@Override
	public void setBlob(int parameterIndex, InputStream inputStream)
			throws SQLException {
		wrappedStatement.setBlob(parameterIndex, inputStream);
	}

	@Override
	public void setBlob(int parameterIndex, InputStream inputStream, long length)
			throws SQLException {
		wrappedStatement.setBlob(parameterIndex, inputStream, length);
	}

	@Override
	public void setBoolean(int parameterIndex, boolean x) throws SQLException {
		addParameter(parameterIndex, x);
		wrappedStatement.setBoolean(parameterIndex, x);
	}

	@Override
	public void setByte(int parameterIndex, byte x) throws SQLException {
		addParameter(parameterIndex, x);
		wrappedStatement.setByte(parameterIndex, x);
	}

	@Override
	public void setBytes(int parameterIndex, byte[] x) throws SQLException {
		wrappedStatement.setBytes(parameterIndex, x);
	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader)
			throws SQLException {
		wrappedStatement.setCharacterStream(parameterIndex, reader);
	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader, int length)
			throws SQLException {
		wrappedStatement.setCharacterStream(parameterIndex, reader, length);
	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader,
			long length) throws SQLException {
		wrappedStatement.setCharacterStream(parameterIndex, reader, length);
	}

	@Override
	public void setClob(int parameterIndex, Clob x) throws SQLException {
		wrappedStatement.setClob(parameterIndex, x);
	}

	@Override
	public void setClob(int parameterIndex, Reader reader) throws SQLException {
		wrappedStatement.setClob(parameterIndex, reader);
	}

	@Override
	public void setClob(int parameterIndex, Reader reader, long length)
			throws SQLException {
		wrappedStatement.setClob(parameterIndex, reader, length);
	}

	@Override
	public void setDate(int parameterIndex, Date x) throws SQLException {
		addParameter(parameterIndex, x);
		wrappedStatement.setDate(parameterIndex, x);
	}

	@Override
	public void setDate(int parameterIndex, Date x, Calendar cal)
			throws SQLException {
		wrappedStatement.setDate(parameterIndex, x, cal);
	}

	@Override
	public void setDouble(int parameterIndex, double x) throws SQLException {
		addParameter(parameterIndex, x);
		wrappedStatement.setDouble(parameterIndex, x);
	}

	@Override
	public void setFloat(int parameterIndex, float x) throws SQLException {
		addParameter(parameterIndex, x);
		wrappedStatement.setFloat(parameterIndex, x);
	}

	@Override
	public void setInt(int parameterIndex, int x) throws SQLException {
		addParameter(parameterIndex, x);
		wrappedStatement.setInt(parameterIndex, x);
	}

	@Override
	public void setLong(int parameterIndex, long x) throws SQLException {
		addParameter(parameterIndex, x);
		wrappedStatement.setLong(parameterIndex, x);
	}

	@Override
	public void setNCharacterStream(int parameterIndex, Reader value)
			throws SQLException {
		wrappedStatement.setNCharacterStream(parameterIndex, value);
	}

	@Override
	public void setNCharacterStream(int parameterIndex, Reader value,
			long length) throws SQLException {
		wrappedStatement.setNCharacterStream(parameterIndex, value, length);
	}

	@Override
	public void setNClob(int parameterIndex, NClob value) throws SQLException {
		wrappedStatement.setNClob(parameterIndex, value);
	}

	@Override
	public void setNClob(int parameterIndex, Reader reader) throws SQLException {
		wrappedStatement.setNClob(parameterIndex, reader);
	}

	@Override
	public void setNClob(int parameterIndex, Reader reader, long length)
			throws SQLException {
		wrappedStatement.setNClob(parameterIndex, reader, length);
	}

	@Override
	public void setNString(int parameterIndex, String value)
			throws SQLException {
		wrappedStatement.setNString(parameterIndex, value);
	}

	@Override
	public void setNull(int parameterIndex, int sqlType) throws SQLException {
		addParameter(parameterIndex, null);
		wrappedStatement.setNull(parameterIndex, sqlType);
	}

	@Override
	public void setNull(int parameterIndex, int sqlType, String typeName)
			throws SQLException {
		wrappedStatement.setNull(parameterIndex, sqlType, typeName);
	}

	@Override
	public void setObject(int parameterIndex, Object x) throws SQLException {
		addParameter(parameterIndex, x);
		wrappedStatement.setObject(parameterIndex, x);
	}

	@Override
	public void setObject(int parameterIndex, Object x, int targetSqlType)
			throws SQLException {
		addParameter(parameterIndex, x);
		wrappedStatement.setObject(parameterIndex, x, targetSqlType);
	}

	@Override
	public void setObject(int parameterIndex, Object x, int targetSqlType,
			int scaleOrLength) throws SQLException {
		wrappedStatement.setObject(parameterIndex, x, targetSqlType,
				scaleOrLength);
	}

	@Override
	public void setRef(int parameterIndex, Ref x) throws SQLException {
		wrappedStatement.setRef(parameterIndex, x);
	}

	@Override
	public void setRowId(int parameterIndex, RowId x) throws SQLException {
		wrappedStatement.setRowId(parameterIndex, x);
	}

	@Override
	public void setSQLXML(int parameterIndex, SQLXML xmlObject)
			throws SQLException {
		wrappedStatement.setSQLXML(parameterIndex, xmlObject);
	}

	@Override
	public void setShort(int parameterIndex, short x) throws SQLException {
		addParameter(parameterIndex, x);
		wrappedStatement.setShort(parameterIndex, x);
	}

	@Override
	public void setString(int parameterIndex, String x) throws SQLException {
		addParameter(parameterIndex, x);
		wrappedStatement.setString(parameterIndex, x);
	}

	@Override
	public void setTime(int parameterIndex, Time x) throws SQLException {
		wrappedStatement.setTime(parameterIndex, x);
	}

	@Override
	public void setTime(int parameterIndex, Time x, Calendar cal)
			throws SQLException {
		wrappedStatement.setTime(parameterIndex, x, cal);
	}

	@Override
	public void setTimestamp(int parameterIndex, Timestamp x)
			throws SQLException {
		addParameter(parameterIndex, x);
		wrappedStatement.setTimestamp(parameterIndex, x);
	}

	@Override
	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
			throws SQLException {
		wrappedStatement.setTimestamp(parameterIndex, x, cal);
	}

	@Override
	public void setURL(int parameterIndex, URL x) throws SQLException {
		wrappedStatement.setURL(parameterIndex, x);
	}

	/** @deprecated */
	@Override
	@Deprecated
	public void setUnicodeStream(int parameterIndex, InputStream x, int length)	throws SQLException {
		wrappedStatement.setUnicodeStream(parameterIndex, x, length);
	}

	@Override
	public void addBatch(String sql) throws SQLException {
		wrappedStatement.addBatch(sql);
	}

	@Override
	public void cancel() throws SQLException {
		wrappedStatement.cancel();
	}

	@Override
	public void clearBatch() throws SQLException {
		wrappedStatement.clearBatch();
	}

	@Override
	public void clearWarnings() throws SQLException {
		wrappedStatement.clearWarnings();
	}

	@Override
	public void close() throws SQLException {
		wrappedStatement.close();
		closed = true;
	}

	@Override
	public boolean execute(String sql) throws SQLException {
		return wrappedStatement.execute(sql);
	}

	@Override
	public boolean execute(String sql, int autoGeneratedKeys)
			throws SQLException {
		return wrappedStatement.execute(sql, autoGeneratedKeys);
	}

	@Override
	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		return wrappedStatement.execute(sql, columnIndexes);
	}

	@Override
	public boolean execute(String sql, String[] columnNames)
			throws SQLException {
		return wrappedStatement.execute(sql, columnNames);
	}

	@Override
	public int[] executeBatch() throws SQLException {
		return wrappedStatement.executeBatch();
	}

	@Override
	public ResultSet executeQuery(String sql) throws SQLException {
		return wrappedStatement.executeQuery(sql);
	}

	@Override
	public int executeUpdate(String sql) throws SQLException {
		return wrappedStatement.executeUpdate(sql);
	}

	@Override
	public int executeUpdate(String sql, int autoGeneratedKeys)
			throws SQLException {
		return wrappedStatement.executeUpdate(sql, autoGeneratedKeys);
	}

	@Override
	public int executeUpdate(String sql, int[] columnIndexes)
			throws SQLException {
		return wrappedStatement.executeUpdate(sql, columnIndexes);
	}

	@Override
	public int executeUpdate(String sql, String[] columnNames)
			throws SQLException {
		return wrappedStatement.executeUpdate(sql, columnNames);
	}

	@Override
	public Connection getConnection() throws SQLException {
		return wrappedStatement.getConnection();
	}

	@Override
	public int getFetchDirection() throws SQLException {
		return wrappedStatement.getFetchDirection();
	}

	@Override
	public int getFetchSize() throws SQLException {
		return wrappedStatement.getFetchSize();
	}

	@Override
	public ResultSet getGeneratedKeys() throws SQLException {
		return wrappedStatement.getGeneratedKeys();
	}

	@Override
	public int getMaxFieldSize() throws SQLException {
		return wrappedStatement.getMaxFieldSize();
	}

	@Override
	public int getMaxRows() throws SQLException {
		return wrappedStatement.getMaxRows();
	}

	@Override
	public boolean getMoreResults() throws SQLException {
		return wrappedStatement.getMoreResults();
	}

	@Override
	public boolean getMoreResults(int current) throws SQLException {
		return wrappedStatement.getMoreResults(current);
	}

	@Override
	public int getQueryTimeout() throws SQLException {
		return wrappedStatement.getQueryTimeout();
	}

	@Override
	public ResultSet getResultSet() throws SQLException {
		return wrappedStatement.getResultSet();
	}

	@Override
	public int getResultSetConcurrency() throws SQLException {
		return wrappedStatement.getResultSetConcurrency();
	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		return wrappedStatement.getResultSetHoldability();
	}

	@Override
	public int getResultSetType() throws SQLException {
		return wrappedStatement.getResultSetType();
	}

	@Override
	public int getUpdateCount() throws SQLException {
		return wrappedStatement.getUpdateCount();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		return wrappedStatement.getWarnings();
	}

	@Override
	public boolean isClosed() throws SQLException {
		return wrappedStatement.isClosed();
	}

	@Override
	public boolean isPoolable() throws SQLException {
		return wrappedStatement.isPoolable();
	}

	@Override
	public void setCursorName(String name) throws SQLException {
		wrappedStatement.setCursorName(name);
	}

	@Override
	public void setEscapeProcessing(boolean enable) throws SQLException {
		wrappedStatement.setEscapeProcessing(enable);
	}

	@Override
	public void setFetchDirection(int direction) throws SQLException {
		wrappedStatement.setFetchDirection(direction);
	}

	@Override
	public void setFetchSize(int rows) throws SQLException {
		wrappedStatement.setFetchSize(rows);
	}

	@Override
	public void setMaxFieldSize(int max) throws SQLException {
		wrappedStatement.setMaxFieldSize(max);
	}

	@Override
	public void setMaxRows(int max) throws SQLException {
		wrappedStatement.setMaxRows(max);
	}

	@Override
	public void setPoolable(boolean poolable) throws SQLException {
		wrappedStatement.setPoolable(poolable);
	}

	@Override
	public void setQueryTimeout(int seconds) throws SQLException {
		wrappedStatement.setQueryTimeout(seconds);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		if (iface.isInstance(this)) {
			return true;
		} else {
			return wrappedStatement.isWrapperFor(iface);
		}
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		if (iface.isInstance(this)) {
			return iface.cast(this);
		} else {
			return wrappedStatement.unwrap(iface);
		}
	}

	/**
	 * Logs a statement.
	 * @param statement the statement
	 * @param start the start time in nanoseconds
	 * @param end the end time in nanoseconds
	 */
	private void logStatement(long start, long end) {
		long ms = (end - start) / 1000000;
		String message = toString() + " took " + ms + " ms";
		if (ms < 100) {
			LOGGER.trace(message);
		} else if (ms < 1000) {
			LOGGER.debug(message);
		} else if (ms < 10000) {
			LOGGER.info(message);
		} else {
			LOGGER.warn(message);
		}

		// Reset start time for the case this statement is used again.
		startTimeNano = end;
	}

	/**
	 * Stores the parameter in the {@link #parameters} list.
	 * @param index the index of the parameter in the query
	 * @param parameter the parameter
	 */
	private void addParameter(int index, Object parameter) {
		index -= 1; // parameter indexes start at 1 instead of 0.
		if (parameters.size() > index) {
			parameters.set(index, parameter);
		} else {
			while (parameters.size() < index) {
				parameters.add(null);
			}
			parameters.add(parameter);
		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if (!closed) {
			LOGGER.error("PreparedStatement " + this + " was not closed before finalization!");
			close();
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(query.length() + 100);
		int paramIndex = 0;
		int index = query.indexOf('?');
		int prevIndex = 0;
		while (index != -1) {
			sb.append(query.substring(prevIndex, index));
			if (paramIndex < parameters.size()) {
				sb.append(parameters.get(paramIndex));
			} else {
				sb.append('?');
			}
			paramIndex += 1;
			prevIndex = index + 1;
			index = query.indexOf('?', prevIndex);
		}
		sb.append(query.substring(prevIndex));
		return sb.toString();
	}
}