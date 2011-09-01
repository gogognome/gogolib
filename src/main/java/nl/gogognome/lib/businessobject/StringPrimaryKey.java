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
package nl.gogognome.lib.businessobject;

/**
 * Base class for primary keys that can be represented by a string.
 * @author Sander Kooijmans
 */
public abstract class StringPrimaryKey implements PrimaryKey {

    private final String id;

    /**
     * Constructor.
     * @param id the primary key value.
     */
    public StringPrimaryKey(String id) {
        super();
        this.id = id;
    }

    @Override
	public String getStringRepresentation() {
        return id;
    }

    /**
     * Gets the id of this primary key.
     * @return
     */
    public String getId() {
        return id;
    }

    @Override
	public boolean equals(Object obj) {
        if (obj instanceof StringPrimaryKey) {
            StringPrimaryKey that = (StringPrimaryKey) obj;
            return this.getId().equals(that.getId());
        }
        return false;
    }

    @Override
	public int hashCode() {
        return id.hashCode();
    }
}
