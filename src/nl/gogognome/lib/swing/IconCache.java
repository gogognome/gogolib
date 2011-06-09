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
package nl.gogognome.lib.swing;

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
