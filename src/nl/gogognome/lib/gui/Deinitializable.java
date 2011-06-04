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
package nl.gogognome.lib.gui;

/**
 * This interface contains a method to deinitialize a component.
 * @author Sander Kooijmans
 */
public interface Deinitializable {

	/**
	 * Call this method to deinitialize the component. The component can free its resources.
	 */
	public void deinitialize();
}
