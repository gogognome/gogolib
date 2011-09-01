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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import nl.gogognome.lib.util.DateUtil;

import org.junit.Test;

/**
 * This class tests the database specific code of the gogolib.
 *
 * @author Sander Kooijmans
 */
public class TestDatabase extends AbstractDatabaseTest {

	@Test
	public void createAndGetTestBO() throws Exception {
		TestBO bo = new TestBO(null);
		bo.setBoolValue(true);
		bo.setDateValue(DateUtil.createDate(2011, 10, 5));
		bo.setIntValue(12345);
		bo.setLongValue(1234567890123456789L);
		bo.setStringValue("Test");

		bo = boService.createBO(bo);
		assertNotNull(bo.getPK());

		TestBO actualBO = boService.findByPK(bo.getPK());
		assertEquals(bo, actualBO);
		assertEquals(bo.getBoolValue(), actualBO.getBoolValue());
		assertEquals(bo.getDateValue(), actualBO.getDateValue());
		assertEquals(bo.getIntValue(), actualBO.getIntValue());
		assertEquals(bo.getLongValue(), actualBO.getLongValue());
		assertEquals(bo.getStringValue(), actualBO.getStringValue());
	}
}
