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
package nl.gogognome.swing;

import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;

/**
 * This class implements a cache for icons.
 */
class IconCache {

	private final static IconCache INSTANCE = new IconCache();

	private Map<String, Icon> map = new HashMap<String, Icon>();

	private IconCache() {

	}

	public static IconCache getInstance() {
		return INSTANCE;
	}

	/**
	 * Gets the icon from the map.
	 * @param id the id of the icon
	 * @return the icon as stored in the cache or <code>null</code> if no icon with the specified id
	 *         is present in the cache.
	 */
	public Icon getIcon(String id) {
		return map.get(id);
	}

	/**
	 * Adds the specified icon to the cache.
	 * @param id the id of the icon
	 * @param icon the icon. If another icon was stored with the same id, then that icon
	 *        will be replaced by this icon.
	 */
	public void addIcon(String id, Icon icon) {
		map.put(id, icon);
	}
}
