/*
    This file is part of gogolib.

    gogolib is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    gogolib is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with gogolib.  If not, see <http://www.gnu.org/licenses/>.
*/
package nl.gogognome.framework.models;

import nl.gogognome.util.ComparatorUtil;

/**
 * This class implements a model for a <code>String</code>.
 *
 * @author Sander Kooijmans
 */
public class StringModel extends AbstractModel {

    private String string;

    public String getString() {
        return string;
    }

    /**
     * Sets the string of this model.
     * @param newString the new value of the string
     * @param source the model change listener that sets the date. It will not
     *         get notified. It may be <code>null</code>.
     */
    public void setString(String newString, ModelChangeListener source) {
        String oldString = this.string;
        if (!ComparatorUtil.equals(oldString, newString)) {
            this.string = newString;
            notifyListeners(source);
        }
    }
}
