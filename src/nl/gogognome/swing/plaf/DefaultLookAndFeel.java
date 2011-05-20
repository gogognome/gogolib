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
package nl.gogognome.swing.plaf;

import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;

/**
 * This class implements the default look and feel for the
 * gogolib-based projects.
 */
public class DefaultLookAndFeel extends MetalLookAndFeel {

    private static final long serialVersionUID = 1L;

    public static void useDefaultLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new DefaultLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
            // Don't let application crash because of an unsupported look and feel.
        }
    }

    /* (non-Javadoc)
     * @see javax.swing.LookAndFeel#getDescription()
     */
    @Override
	public String getDescription() {
        return "The gogolib look and feel";
    }

    /* (non-Javadoc)
     * @see javax.swing.LookAndFeel#getID()
     */
    @Override
	public String getID() {
        return "gogolib";
    }

    /* (non-Javadoc)
     * @see javax.swing.LookAndFeel#getName()
     */
    @Override
	public String getName() {
        return "The gogolib look and feel";
    }

    /* (non-Javadoc)
     * @see javax.swing.LookAndFeel#isNativeLookAndFeel()
     */
    @Override
	public boolean isNativeLookAndFeel() {
        return false;
    }

    /* (non-Javadoc)
     * @see javax.swing.LookAndFeel#isSupportedLookAndFeel()
     */
    @Override
	public boolean isSupportedLookAndFeel() {
        return true;
    }

    /**
     * This method is called once by <code>UIManager.setLookAndFeel</code> to create
     * the look and feel specific defaults table. Other applications, for example
     * an application builder, may also call this method.
     */
    @Override
	public UIDefaults getDefaults() {
        UIDefaults defaults = super.getDefaults();
        defaults.put("TextFieldUI", DefaultTextFieldUI.class.getName());
        defaults.put("TabbedPaneUI", DefaultTabbedPaneUI.class.getName());
        defaults.put("TableUI", DefaultTableUI.class.getName());
        return defaults;
    }
}
