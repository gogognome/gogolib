/*
 * $Id: JComboBoxWithKeyboardInput.java,v 1.1 2007-01-15 18:33:07 sanderk Exp $
 *
 * Copyright (C) 2006 Sander Kooijmans
 */
package nl.gogognome.swing;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.JComboBox;

/**
 * This class extends the standard combo box implementation with keyboard input:
 * the user can select an item by typing a substring of the items name.
 * 
 * @author Sander Kooijmans
 */
public class JComboBoxWithKeyboardInput extends JComboBox implements KeyListener, FocusListener
{
    /** 
     * Contains the string representation of the parties in the <code>parties</code>
     * field. All strings are converted to lower case to make case-insensitive
     * searches easy.
     */ 
    private Vector itemStrings = new Vector();
    
	/** The substring entered by the user. */
	private StringBuffer substring = new StringBuffer();
    
    /**
     * Constructor. 
     */
    public JComboBoxWithKeyboardInput() 
    {
        super();
		addKeyListener(this);
		addFocusListener(this);
    }

    public void addItem(Object item)
    {
        super.addItem(item);
        itemStrings.addElement(item.toString().toLowerCase());
    }
    
	/**
	 * This method is called when a key is pressed.
	 * @param e the key event.
	 */
	public void keyPressed(KeyEvent e) 
	{
		char c = e.getKeyChar();
		if (Character.isLetterOrDigit(c) ) 
		{
			substring.append( Character.toLowerCase(c) );
			selectItemWithSubstring(substring.toString());
		} 
		else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) 
		{
			if (substring.length() > 0) 
			{
				substring.deleteCharAt( substring.length()-1 );
				selectItemWithSubstring(substring.toString());
			}
		}
	}

	/**
	 * Selects the first party in the list that has the specified string as
	 * substring. If the specified string is not a substring of any party, then
	 * the currently selected party stays selected.
	 * 
	 * @param s the string that should be matched as substring.
	 */
	private void selectItemWithSubstring( String s ) {
		for (int i=0; i<itemStrings.size(); i++) 
		{
			if (((String)itemStrings.elementAt(i)).indexOf(s) != -1) 
			{
				setSelectedIndex(i);
				return;
			}
		}
	}
	
	/**
	 * This method is called when a key is released.
	 * @param e the key event.
	 */
	public void keyReleased(KeyEvent e) 
	{
		// ignore this event
	}

	/**
	 * This method is called when a key is typed.
	 * @param e the key event.
	 */
	public void keyTyped(KeyEvent e) 
	{
		// ignore this event
	}
	
	/**
	 * This method is called when a field gains focus.
	 * @param event the event.
	 */
	public void focusGained(FocusEvent event) 
	{
		substring.delete(0, substring.length());
	}

	/**
	 * This method is called when a field loses focus.
	 * @param event the event.
	 */
	public void focusLost(FocusEvent event) 
	{
		// ignore
	}
}
