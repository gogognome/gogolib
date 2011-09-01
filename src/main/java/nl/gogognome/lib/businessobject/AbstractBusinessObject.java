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

import nl.gogognome.lib.util.ComparatorUtil;

/**
 * This is the base class for all business object classes.
 *
 * @author Sander Kooijmans
 */
public class AbstractBusinessObject<P extends PrimaryKey> implements BusinessObject<P> {

	private P pk;

	/**
	 * Constructs a business object with the specified primary key.
	 * @param pk the primary key
	 */
    public AbstractBusinessObject(P pk) {
		super();
		this.pk = pk;
	}

    @Override
	public P getPK() {
    	return pk;
    }

    /**
     * Sets the primary key for this business object. This method can only
     * be used to set a primary key on a business object that does not have
     * a primary key yet. If the business object already has a primary key,
     * the this method throws an IllegalStateException.
     * @param pk the primary key
     * @throws IllegalStateException if getPK() != null
     */
    public void setPK(P pk) {
    	if (this.pk != null) {
    		throw new IllegalStateException("Cannot change PK of business object "
    				+ this.pk.getPresentationName());
    	}
		this.pk = pk;
	}

    @Override
	public boolean equals(Object obj) {
        if (obj != null && obj.getClass().equals(this.getClass()) && getPK() != null) {
            return ComparatorUtil.equals(getPK(), ((BusinessObject<P>)obj).getPK());
        }
        return false;
    }

    @Override
	public int hashCode() {
        return getPK().hashCode();
    }

    @Override
	public String toString() {
        PrimaryKey pk = getPK();
        if (pk != null) {
            return getPK().getPresentationName();
        } else {
            return getClass().getName() + " without PK " + System.identityHashCode(this);
        }
    }
}
