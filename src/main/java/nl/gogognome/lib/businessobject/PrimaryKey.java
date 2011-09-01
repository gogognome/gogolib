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
 * This interface specifies a primary key for business objects.
 */
public interface PrimaryKey {

    /**
     * Gets a string representation of the primary key. This string can be
     * used to serialize this primary key.
     * @return a string representation of the primary key
     */
    public String getStringRepresentation();

    /**
     * Gets a description of this PK that can be used in communications with a user.
     *
     * @return a description mentioning the type of the business object this PK is used for
     *         and a description of the unique value of this PK.
     */
    public String getPresentationName();
}
