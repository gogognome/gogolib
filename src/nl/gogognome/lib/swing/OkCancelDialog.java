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
package nl.gogognome.lib.swing;

import java.awt.Dialog;
import java.awt.Frame;

import nl.gogognome.lib.swing.views.View;


/**
 * This class implements a dialog with two buttons: OK and Cancel.
 *
 * <p>Subclasses must implement the <tt>handleOk()</tt> method and should
 * implement <tt>handleCancel()</tt> as well. Subclasses must not (and can not)
 * implement or call the method <tt>handleButton(int)</tt>.
 *
 * @author Sander Kooijmans
 */
public abstract class OkCancelDialog extends DialogWithButtons {

    /** Indicates whether the dialog was exited with the Ok button. */
    private boolean exitedWithOk;

	/**
	 * Creates a dialog with one or more buttons.
	 * @param frame the frame that owns this dialog.
	 * @param titleId the id of the title string.
	 * @param buttonIds the ids of the buttons.
	 */
	protected OkCancelDialog(Frame frame, String titleId) {
		super(frame, titleId, BT_OK_CANCEL);
	}

	/**
	 * Creates a dialog with one or more buttons.
	 * @param dialog the dialog that owns this dialog.
	 * @param titleId the id of the title string.
	 * @param buttonIds the ids of the buttons.
	 */
	protected OkCancelDialog(Dialog dialog, String titleId) {
		super(dialog, titleId, BT_OK_CANCEL);
	}

    /**
     * Creates a dialog with one or more buttons.
     * @param view the view that owns this dialog.
     * @param titleId the id of the title string.
     * @param buttonIds the ids of the buttons.
     */
    protected OkCancelDialog(View view, String titleId) {
        super(view, titleId, BT_OK_CANCEL);
    }

	/**
	 * Handles the button-pressed event. This method is called when one of the buttons
	 * has been pressed by the user.
	 *
	 * @param index the index of the button: 0 for Ok, 1 for Cancel.
	 */
	@Override
	final protected void handleButton(int index) {
		if (index == 0) {
            exitedWithOk = true;
			handleOk();
		} else {
			handleCancel();
		}
	}

	/**
	 * Handles the OK event.
	 */
	protected abstract void handleOk();

    /**
     * Checks whether the dialog was exited with the Ok button.
     * @return <code>true</code> if the dialog was exited with the Ok button;
     *         <code>false</code> oterhwise
     */
    public boolean isExitedWithOk() {
        return exitedWithOk;
    }
}
