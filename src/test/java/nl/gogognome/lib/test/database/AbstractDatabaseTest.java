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

import java.util.concurrent.atomic.AtomicInteger;

import nl.gogognome.lib.dao.DBMSSpecificDAOPlugin;
import nl.gogognome.lib.dao.HsqlDbDAOPlugin;
import nl.gogognome.lib.dbconnection.ConnectionPool;
import nl.gogognome.lib.service.BusinessObjectService;
import nl.gogognome.lib.util.Factory;

import org.junit.After;
import org.junit.Before;

/**
 * This class is the base class for all tests that use the database.
 * It sets up an in-memory database with the MF-Tool tables per test case.
 * It also initializes all services for each test case.
 *
 * @author Sander Kooijmans
 */
public class AbstractDatabaseTest {

	private final static AtomicInteger DATABASE_NR = new AtomicInteger();

	protected BusinessObjectService boService;

	@Before
	public void initDatabaseAndServices() throws Exception {
		try {
			ConnectionPool connectionPool = new ConnectionPool();
			Factory.bindSingleton(ConnectionPool.class, connectionPool);
			Factory.bindClass(DBMSSpecificDAOPlugin.class, HsqlDbDAOPlugin.class);

			int databaseNr = DATABASE_NR.incrementAndGet();
			connectionPool.setJdbcConnectionParameters(
					"jdbc:hsqldb:mem:test" + databaseNr + ";shutdown=true", "SA", "");


			BusinessObjectService.registerBO(TestBO.class, TestPK.class, TestDAO.class);
			boService = new BusinessObjectService();
			boService.createRegisteredTables();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
	}

	@After
	public void dropTables() throws Exception {
		boService.dropRegisteredTables();
	}
}
