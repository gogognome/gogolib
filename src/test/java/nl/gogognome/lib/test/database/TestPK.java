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

import nl.gogognome.lib.businessobject.IntegerPrimaryKey;

/**
 * PK for the test business object.
 * @author Sander Kooijmans
 */
public class TestPK extends IntegerPrimaryKey {
	public TestPK(int id) {
		super(id);
	}
}
