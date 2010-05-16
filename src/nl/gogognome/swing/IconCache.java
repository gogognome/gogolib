/**
 *
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
