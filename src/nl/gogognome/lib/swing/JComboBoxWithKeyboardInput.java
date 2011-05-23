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

    @Override
	public void addItem(Object item)
    {
        super.addItem(item);
        itemStrings.addElement(item.toString().toLowerCase());
    }

	/**
	 * This method is called when a key is pressed.
	 * @param e the key event.
	 */
	@Override
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
	@Override
	public void keyReleased(KeyEvent e)
	{
		// ignore this event
	}

	/**
	 * This method is called when a key is typed.
	 * @param e the key event.
	 */
	@Override
	public void keyTyped(KeyEvent e)
	{
		// ignore this event
	}

	/**
	 * This method is called when a field gains focus.
	 * @param event the event.
	 */
	@Override
	public void focusGained(FocusEvent event)
	{
		substring.delete(0, substring.length());
	}

	/**
	 * This method is called when a field loses focus.
	 * @param event the event.
	 */
	@Override
	public void focusLost(FocusEvent event)
	{
		// ignore
	}
}
