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
package nl.gogognome.framework;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * This class implements a container for a view.
 *
 * <p>If a view is added to a dialog, then closing the view will automatically close the dialog.
 * If a wizzard-like dialog must be made, in which a sequence of views are shown, then add
 * a <code>ViewContainer</code> to the dialog and show the sequences of views in this
 * <code>ViewContainer</code>. After showing the last view, close the <code>ViewContainer</code>
 * to close the dialog.
 */
public class ViewContainer extends View {

    /** The view that is shown in this <code>ViewContainer</code>. */
    private View view;

    /** The title of this <code>ViewContainer</code>. */
    private String title;

    /**
     * Constructor.
     * @param title the title of this <code>ViewContainer</code>
     */
    public ViewContainer(String title) {
        this.title = title;
    }

    /**
     * Gets the title of this view.
     * @return the title
     */
    @Override
	public String getTitle() {
        return title;
    }

    /** This method is called when the <code>ViewContainer</code> is closed. */
    @Override
	public void onClose() {
        removeView();
    }

    /** This method is called when the <code>ViewContainer</code> is initialized. */
    @Override
	public void onInit() {
        setLayout(new BorderLayout());
    }

    /**
     * Sets the view on this <code>ViewContainer</code>.
     * If there is another view set on this view, that view will be closed first.
     * @param view the view
     */
    public void setView(View view) {
        removeView();
        this.view = view;
        view.setParentDialog(getParentDialog());
        view.setParentFrame(getParentFrame());
        view.setCloseAction(new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {
                removeView();
            }
        });
        view.doInit();
        add(view, BorderLayout.CENTER);
        validate();
    }

    /** Removes the view from this <code>ViewContainer</code>. */
    public void removeView() {
        View oldView = view;
        view = null;
        if (oldView != null) {
            remove(oldView);
            oldView.doClose();
        }
    }
}
