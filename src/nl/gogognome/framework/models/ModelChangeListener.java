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

/**
 * This interface specifies a listener for changes in a model.
 *
 * @author Sander Kooijmans
 */
public interface ModelChangeListener {

    /**
     * This method is called when the model has changed
     * @param model the model that has changed
     */
    public void modelChanged(AbstractModel model);
}
