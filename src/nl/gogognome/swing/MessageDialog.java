package nl.gogognome.swing;

import java.awt.*;

import javax.swing.JLabel;


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
	 * @param message the message to be shown.
	 */	
	private void showDialog( String message ) 
	{
		componentInitialized(new JLabel(message));
		super.showDialog();
	}
}
