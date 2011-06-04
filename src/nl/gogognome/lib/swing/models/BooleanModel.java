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
package nl.gogognome.lib.swing.models;


/**
 * This class implements a model for a <code>Boolean</code>.
 *
 * @author Sander Kooijmans
 */
public class BooleanModel extends AbstractModel {

    private boolean value;

    public boolean getBoolean() {
        return value;
    }

    /**
     * Sets the boolean of this model.
     * @param newValue the new value of the boolean
     * @param source the model change listener that sets the boolean. It will not
     *         get notified. It may be <code>null</code>.
     */
    public void setBoolean(boolean newValue, ModelChangeListener source) {
        boolean oldValue = this.value;
        if (oldValue != newValue) {
            this.value = newValue;
            notifyListeners(source);
        }
    }
}
