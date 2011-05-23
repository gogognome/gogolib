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
package nl.gogognome.lib.swing.views;

import java.awt.Window;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A view can be shown in a dialog or in a frame.
 *
 * @author Sander Kooijmans
 */
public abstract class View extends JPanel {

    /** The subscribed listeners. */
    private ArrayList<ViewListener> listeners = new ArrayList<ViewListener>();

    /**
     * This action closes the view. The action will be set before the view
     * is shown. The view can use it let itself be closed.
     */
    protected Action closeAction;

    /** The parent frame that contains this view. */
    private JFrame parentFrame;

    /** The parent dialog that contains this view. */
    private JDialog parentDialog;

    /** The default button of this view. */
    private JButton defaultButton;

    /**
     * Gets the title of the view.
     * @return the title of the view
     */
    public abstract String getTitle();

    /**
     * Gets the default button of this view.
     * @return the default button of this view or <code>null</code> if this view
     *         has no default button
     */
    JButton getDefaultButton() {
        return defaultButton;
    }

    /** This method is called before the view is shown. It initializes the view. */
    public abstract void onInit();

    /** This method is called just before the view is closed. It can free resources. */
    public abstract void onClose();

    /**
     * Sets the close action.
     *
     * @param closeAction the close action
     */
    public void setCloseAction(Action closeAction) {
        this.closeAction = closeAction;
    }

    public void setParentFrame(JFrame frame) {
        parentFrame = frame;
    }

    /**
     * Returns the parent container of the view.
     * @return the parent container of the view
     */
    public Window getParentWindow() {
        if (parentDialog != null) {
            return parentDialog;
        } else {
            return parentFrame;
        }
    }

    public JFrame getParentFrame() {
        return parentFrame;
    }

    public void setParentDialog(JDialog dialog) {
        parentDialog = dialog;
    }

    public JDialog getParentDialog() {
        return parentDialog;
    }

    public void addViewListener(ViewListener listener) {
        listeners.add(listener);
    }

    public void removeViewListener(ViewListener listener) {
        listeners.remove(listener);
    }

    /**
     * Sets the default button of this view.
     * @param button the default button
     */
    public void setDefaultButton(JButton button) {
        defaultButton = button;
        if (parentFrame != null) {
            parentFrame.getRootPane().setDefaultButton(button);
        } else if (parentDialog != null) {
            parentDialog.getRootPane().setDefaultButton(button);
        }
    }

    /**
     * Closes the view and notifies listeners.
     */
    void doClose() {
        onClose();
        ViewListener[] tempListeners = listeners.toArray(new ViewListener[listeners.size()]);
        for (int i = 0; i < tempListeners.length; i++) {
            tempListeners[i].onViewClosed(this);
        }
    }

    /** Initializes the view. */
    void doInit() {
        onInit();
    }
}
