package nl.gogognome.swing;

import java.awt.*;

import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * This class implements dialog used to show a message to the user. 
 * 
 * @author Sander Kooijmans
 */
public class MessageDialog extends DialogWithButtons 
{
	/**
	 * Constructor.
	 * 
	 * @param owner the owner of this dialog.
	 * @param titleId the id of the title string.
	 * @param message the message to be shown to the user.
	 */
	public MessageDialog(Frame owner, String titleId, String message) 
	{
		this(owner, titleId, message, BT_OK );
	}

	/**
	 * Constructor.
	 * 
	 * @param owner the owner of this dialog.
	 * @param titleId the id of the title string.
	 * @param message the message to be shown to the user.
	 * @param buttonIds the ids of the buttons.
	 */
	public MessageDialog(Frame owner, String titleId, String message, String[] buttonIds) 
	{
		super(owner, titleId, buttonIds );
		showDialog(message);
	}

	/**
	 * Constructor.
	 * 
	 * @param owner the owner of this dialog.
	 * @param titleId the id of the title string.
	 * @param message the message to be shown to the user.
	 */
	public MessageDialog(Dialog owner, String titleId, String message) 
	{
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
	public MessageDialog(Dialog owner, String titleId, String message, String[] buttonIds) 
	{
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
    public MessageDialog(Frame owner, String titleId, Throwable t) 
    {
        this(owner, titleId, t.getMessage(), BT_OK);
        t.printStackTrace();
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
	 * @param owner the owner of this dialog.
	 * @param titleId the id of the title string.
	 * @param message the message to be shown to the user.
	 */
	public static void showMessage(Frame owner, String titleId, String message) {
	    new MessageDialog(owner, titleId, message);
	}
}
