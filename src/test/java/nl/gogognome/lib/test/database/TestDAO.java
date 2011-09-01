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

import java.sql.Connection;
import java.sql.SQLException;

import nl.gogognome.lib.dao.AbstractBusinessObjectDAO;
import nl.gogognome.lib.dao.ColumnValuePairs;
import nl.gogognome.lib.dao.Record;

/**
 * DAO for the test business object.
 *
 * @author Sander Kooijmans
 */
public class TestDAO extends AbstractBusinessObjectDAO<TestPK, TestBO> {

	public TestDAO(Connection connection) {
		super(connection, new TestTable());
	}

	@Override
	public ColumnValuePairs getPKColumnsAndValues(TestPK pk) throws SQLException {
		return ColumnValuePairs.of(TestTable.ID, pk);
	}

	public TestBO create(TestBO bo) throws SQLException {
		return createBO(bo);
	}

	@Override
	protected TestBO updateCreatedBO(TestBO bo, Record record)
			throws SQLException {
		bo.setPK(new TestPK(record.getInt(TestTable.ID)));
		return super.updateCreatedBO(bo, record);
	}

	@Override
	public TestBO convert(Record record) throws SQLException {
		TestPK pk = new TestPK(record.getInt(TestTable.ID));
		TestBO bo = new TestBO(pk);
		bo.setBoolValue(record.getBoolean(TestTable.BOOL));
		bo.setDateValue(record.getDate(TestTable.DATE));
		bo.setIntValue(record.getInt(TestTable.INT));
		bo.setLongValue(record.getLong(TestTable.LONG));
		bo.setStringValue(record.getString(TestTable.STRING_NOT_NULL));
		return bo;
	}

	@Override
	public ColumnValuePairs convert(TestBO bo) throws SQLException {
		ColumnValuePairs cvp = getPKColumnsAndValues(bo.getPK());
		cvp.add(TestTable.BOOL, bo.getBoolValue());
		cvp.add(TestTable.DATE, bo.getDateValue());
		cvp.add(TestTable.INT, bo.getIntValue());
		cvp.add(TestTable.LONG, bo.getLongValue());
		cvp.add(TestTable.STRING_NOT_NULL, bo.getStringValue());
		return cvp;
	}

}
