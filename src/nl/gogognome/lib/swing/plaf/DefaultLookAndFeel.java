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
package nl.gogognome.lib.swing.plaf;

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
