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

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;

/**
 * This class wraps an <code>AbstractAction</code>. It allows to change
 * the behaviour of the <code>actionPerformed()</code> method dynamically.
 *
 * @author Sander Kooijmans
 */
public class ActionWrapper extends AbstractAction {

    /**
     * The action to which <code>actionPerformed()</code> is forwarded.
     * If <code>action</code> is <code>null</code>, then
     * <code>actionPerformed()</code> will do noting.
     */
    private Action action;

    /**
     * Constructor.
     */
    public ActionWrapper() {
        super();
    }

    /**
     * Constructor.
     */
    public ActionWrapper(String name) {
        super(name);
    }

    /**
     * Constructor.
     */
    public ActionWrapper(String name, Icon icon) {
        super(name, icon);
    }

    public void setAction(Action action) {
        this.action = action;
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
	public void actionPerformed(ActionEvent event) {
        // By using a copy of the action variable synchronization problems are prevented.
        Action localAction = action;
        if (localAction != null) {
            localAction.actionPerformed(event);
        }
    }
}
