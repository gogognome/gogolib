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

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/**
 * This class implements a tabbed pane that can hold <code>View</code>s.
 *
 * @author Sander Kooijmans
 */
public class ViewTabbedPane extends JTabbedPane {

    /** Contains the views that are added to this tabbed pane. */
    private ArrayList<View> views = new ArrayList<View>();

    /** The frame that contains this tabbed pane. */
    private JFrame parentFrame;

    /**
     * Constructor.
     * @param parentFrame the frame that contains this tabbed pane.
     */
    public ViewTabbedPane(JFrame parentFrame) {
        super();
        this.parentFrame = parentFrame;
    }

    /**
     * Adds a view to the tabbed pane.
     * @param view the view to be added
     */
    public void openView(final View view) {
        Action closeAction = new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent event) {
                remove(view);
            }
        };

        view.setCloseAction(closeAction);
        view.setParentFrame(parentFrame);
        view.doInit();
        addTab(view.getTitle(), view);
        views.add(view);
        setDefaultButtonForView(view);
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                view.requestFocusInWindow();
//            }
//        });
    }

    /**
     * Removes a view from the tabbed pane.
     * @param view the view to be removed
     */
    public void closeView(View view) {
        if (views.contains(view)) {
            view.doClose();
            remove(view);
            views.remove(view);
        }
    }

    /**
     * Select the tab that contains the specified view.
     * @param view the view
     */
    public void selectView(View view) {
        int index = getIndexOfView(view);
        if (index != -1) {
            setSelectedIndex(index);
        }
    }

    /** Closes all views in the tabbed pane. */
    public void closeAllViews() {
        for (Iterator iter = views.iterator(); iter.hasNext();) {
            View view = (View) iter.next();
            view.doClose();
            super.remove(view);
        }
        views.clear();
    }

    /**
     * Checks whether the specified view is present in this tabbed pane.
     * @param view the view
     * @return <code>true</code> if the specified view is present;
     *         <code>false</code> otherwise
     */
    public boolean hasView(View view) {
        return getIndexOfView(view) != -1;
    }

    public void remove(View view) {
        int index = getIndexOfView(view);
        if (index != -1) {
            remove(index);
        }
    }

    /**
     * This method can be called by the UI when the user closes the tab.
     * @param index index of the tab to be closed
     */
    @Override
	public void remove(int index) {
        View view = (View)getComponentAt(index);
        view.doClose();
        views.remove(view);
        super.remove(index);
    }

    /**
     * Sets the default button for the view.
     * @param view the view
     */
    private void setDefaultButtonForView(View view) {
        Container topLevelContainer = getTopLevelAncestor();
        if (topLevelContainer instanceof JFrame) {
            ((JFrame) topLevelContainer).getRootPane().setDefaultButton(view.getDefaultButton());
        }
    }

    /**
     * Gets the index of the view in the tabbed pane.
     * @param view the view
     * @return the index or -1 if the view is not part of this tabbed pane
     */
    private int getIndexOfView(View view) {
        int count = getTabCount();
        for (int i=0; i<count; i++) {
            if (getComponentAt(i).equals(view)) {
                return i;
            }
        }
        return -1;
    }
}
