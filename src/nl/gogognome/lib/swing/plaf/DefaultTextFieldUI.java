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
package nl.gogognome.lib.swing.plaf;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTextFieldUI;

/**
 * Default UI for a text field.
 */
public class DefaultTextFieldUI extends BasicTextFieldUI {

    private FocusListener focusListener;

    public static ComponentUI createUI(JComponent c) {
        return new DefaultTextFieldUI();
    }

    @Override
	public void installUI(JComponent c) {
        super.installUI(c);
        c.setFocusable(true);

        final JTextField textField = (JTextField) c;
        focusListener = new FocusListener() {

            @Override
			public void focusGained(FocusEvent e) {
                textField.selectAll();
            }

            @Override
			public void focusLost(FocusEvent e) {
                textField.select(0, 0);
            }

        };
        c.addFocusListener(focusListener);
    }

    @Override
	public void uninstallUI(JComponent c) {
        c.removeFocusListener(focusListener);
        super.uninstallUI(c);
    }
}
