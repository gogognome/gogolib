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

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nl.gogognome.lib.swing.views.View;
import nl.gogognome.lib.text.TextResource;


/**
 * This class implements dialog used to show a message to the user.
 *
 * @author Sander Kooijmans
 */
public class MessageDialog extends DialogWithButtons {

	/**
	 * Constructor.
	 *
	 * @param owner the owner of this dialog.
	 * @param titleId the id of the title string.
	 * @param message the message to be shown to the user.
	 */
	public MessageDialog(Frame owner, String titleId, String message) {
		this(owner, titleId, message, BT_OK);
	}

	/**
	 * Constructor.
	 *
	 * @param owner the owner of this dialog.
	 * @param titleId the id of the title string.
	 * @param message the message to be shown to the user.
	 * @param buttonIds the ids of the buttons.
	 */
	public MessageDialog(Frame owner, String titleId, String message, String[] buttonIds) {
		super(owner, titleId, buttonIds);
		showDialog(message);
	}

	/**
	 * Constructor.
	 *
	 * @param owner the owner of this dialog.
	 * @param titleId the id of the title string.
	 * @param message the message to be shown to the user.
	 */
	public MessageDialog(Dialog owner, String titleId, String message) {
		this(owner, titleId, message, BT_OK);
	}

	/**
	 * Constructor.
	 *
	 * @param owner the owner of this dialog.
	 * @param titleId the id of the title string.
	 * @param message the message to be shown to the user.
	 * @param buttonIds the ids of the buttons.
	 */
	public MessageDialog(Dialog owner, String titleId, String message, String[] buttonIds) {
		super(owner, titleId, buttonIds);
		showDialog(message);
	}

    /**
     * Constructor.
     *
     * @param owner the owner of this dialog.
     * @param titleId the id of the title string.
     * @param t throwable whose message will be shown to the user.
     */
    public MessageDialog(Frame owner, String titleId, Throwable t) {
        this(owner, titleId, t.getMessage(), BT_OK);
        t.printStackTrace();
    }

    /**
     * Constructor.
     *
     * @param owner the owner of this dialog.
     * @param titleId the id of the title string.
     * @param message the message to be shown to the user.
     * @param buttonIds the ids of the buttons.
     * @return the dialog that was shown. Use the returned dialog to find out which button was
     *         used to close the dialog
     */
    public static MessageDialog showMessage(View owner, String titleId, String message, String[] buttonIds) {
        if (owner.getParentDialog() != null) {
            return new MessageDialog(owner.getParentDialog(), titleId, message, buttonIds);
        } else {
            return new MessageDialog(owner.getParentFrame(), titleId, message, buttonIds);
        }
    }

	/**
	 * Shows the dialog.
	 * @param message the message to be shown. The sequence of the characters
	 *        '\\' and 'n' indicate a line break.
	 */
	private void showDialog( String message ) {
	    String[] lines = message.split("\\n");
	    Component messageComponent;
	    if (lines.length > 1) {
		    JPanel panel = new JPanel(new GridLayout(lines.length, 1));
		    for (int i = 0; i < lines.length; i++) {
                panel.add(new JLabel(lines[i]));
            }
	        messageComponent = panel;
	    } else {
	        messageComponent = new JLabel(message);
	    }
		componentInitialized(messageComponent);
		super.showDialog();
	}

	/**
	 * Shows a message dialog.
	 * @param owner the owner of this dialog. Must be a <code>JDialog</code> or <code>JFrame</code>
	 * @param titleId the id of the title string.
	 * @param message the message to be shown to the user.
	 */
	public static MessageDialog showMessage(Window owner, String titleId, String message) {
        if (owner instanceof JDialog) {
            return new MessageDialog((JDialog) owner, titleId, message);
        } else {
            return new MessageDialog((JFrame) owner, titleId, message);
        }
	}

	/**
	 * Shows a message dialog based on a {@link Throwable}.
	 * @param owner the owner of this dialog. Must be a <code>JDialog</code> or <code>JFrame</code>
	 * @param t the throwable whose message is shown
	 */
	public static MessageDialog showMessage(Window owner, Throwable t) {
		String message = t.getMessage();
		if (t.getCause() != null) {
			message += ' ' + t.getCause().getMessage();
		}
		return showMessage(owner, "gen.error", message);
	}

    /**
     * Shows a message dialog.
     * @param owner the owner of this dialog.
     * @param titleId the id of the title string.
     * @param message the message to be shown to the user.
     */
    public static MessageDialog showMessage(View owner, String titleId, String message) {
        if (owner.getParentDialog() != null) {
            return new MessageDialog(owner.getParentDialog(), titleId, message);
        } else {
            return new MessageDialog(owner.getParentFrame(), titleId, message);
        }
    }

    /**
     * Shows an error message dialog.
     * @param owner the owner of this dialog.
     * @param messageId the id of the message
     * @param args optional arguments to be filled in the placeholders of the message
     */
    public static MessageDialog showErrorMessage(View owner, String messageId, Object... args) {
    	String message = TextResource.getInstance().getString(messageId, args);
        if (owner.getParentDialog() != null) {
            return new MessageDialog(owner.getParentDialog(), "gen.titleError", message);
        } else {
            return new MessageDialog(owner.getParentFrame(), "gen.titleError", message);
        }
    }

    /**
     * Shows an error message dialog.
     * @param owner the owner of this dialog.
     * @param t throwable whose message is
     * @param messageId the id of the message
     * @param args optional arguments to be filled in the placeholders of the message
     */
    public static MessageDialog showErrorMessage(View owner, Throwable t, String messageId, Object... args) {
    	String message = TextResource.getInstance().getString(messageId, args);
    	// TODO: Show exception in message
        if (owner.getParentDialog() != null) {
            return new MessageDialog(owner.getParentDialog(), "gen.titleError", message);
        } else {
            return new MessageDialog(owner.getParentFrame(), "gen.titleError", message);
        }
    }

}
