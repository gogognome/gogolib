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

import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.net.URL;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import nl.gogognome.text.TextResource;

/**
 * This class is a factory for buttons, menus, menu items, text fields and
 * check boxes.
 *
 * @author kooijmas
 */
public class WidgetFactory {

	/** The singleton instance of this class. */
	private static WidgetFactory instance;

	/** The <code>TextResource</code> used to obtain string resources. */
	private TextResource textResource;

	/**
	 * Gets the singleton instance of this class.
	 * @return the singleton instance of this class.
	 */
	public static synchronized WidgetFactory getInstance()
	{
		if (instance == null)
		{
			instance = new WidgetFactory();
		}
		return instance;
	}

	/** Private constructor to enforce usage of <tt>getInstance()</tt>. */
	private WidgetFactory()
	{
		textResource = TextResource.getInstance();
	}

	/**
	 * Creates an <code>Action</code> for the specified identifier.
	 * @param id the identifier
	 * @return the <code>Action</code> or <code>null</code> if the specified
	 *          identifier does not occur in the resources.
	 */
	public ActionWrapper createAction(String id) {

	    String name = textResource.getString(id);

	    ActionWrapper action;
	    if (name != null) {
		    Icon icon = createIcon(id + ".icon16");
	        if (icon != null) {
	            action = new ActionWrapper(name, icon);
	        } else {
	            action = new ActionWrapper(name);
	        }
	    } else {
	        action = new ActionWrapper();
	    }

	    String s = textResource.getString(id + ".accelerator");
	    KeyStroke accelerator = s != null ? KeyStroke.getKeyStroke(s) : null;
	    if (accelerator != null) {
	        action.putValue(Action.ACCELERATOR_KEY, accelerator);
	    }

	    s = textResource.getString(id + ".mnemonic");
	    int mnemonic = getMnemonic(id);
	    if (mnemonic != -1) {
	        action.putValue(Action.MNEMONIC_KEY, new Integer(mnemonic));
	    }

	    s = textResource.getString(id + ".tooltip");
	    if (s != null) {
	        action.putValue(Action.SHORT_DESCRIPTION, s);
	    }

	    s = textResource.getString(id + ".contexthelp");
	    if (s != null) {
	        action.putValue(Action.LONG_DESCRIPTION, s);
	    }

	    return action;
	}

	/**
	 * Creates a button.
	 * @param id the id of the button's description in the resources.
	 * @action the action to be performed when the button is pressed
	 * @return the button.
	 */
	public JButton createButton(String id, Action action) {
	    ActionWrapper actionWrapper = createAction(id);
	    actionWrapper.setAction(action);

		return new JButton(actionWrapper);
	}

	/**
	 * Creates a label with the specified text.
	 *
	 * @param id the id of the text.
	 * @return the label.
	 */
	public JLabel createLabel(String id) {
		return createLabel(id, null);
	}

    /**
     * Creates a label with the specified text. If <code>component != null</code> then this
     * component receives the focus after the user entered the mnemonic for this label.
     *
     * @param id the id of the text.
     * @param component the component that receives focus after the mnemonic for the
     *        label has been entered
     * @return the label.
     */
    public JLabel createLabel(String id, JComponent component) {
        JLabel label = new JLabel(textResource.getString(id));

        String s = textResource.getString(id + ".mnemonic");
        if (s != null && s.length() == 1 && component != null) {
            label.setDisplayedMnemonic(s.charAt(0));
            label.setLabelFor(component);
        }

        return label;
    }

	/**
	 * Creates a text field. When the created text field gains focus, its
	 * contents is selected.
	 *
	 * @return the text field.
	 */
	public JTextField createTextField() {
		JTextField textField = new JTextField();
		return textField;
	}

	/**
	 * Creates a text field. When the created text field gains focus, its
	 * contents is selected.
	 *
	 * @param columns the number of columns of the text field.
	 * @return the text field.
	 */
	public JTextField createTextField( int columns )
	{
		JTextField textField = new JTextField(columns);
        textField.setFocusable(true);
		return textField;
	}

	/**
	 * Creates a text field. When the created text field gains focus, its
	 * contents is selected.
	 *
	 * @param text the initial text of the text field.
	 * @return the text field.
	 */
	public JTextField createTextField( String text )
	{
		JTextField textField = new JTextField(text);
		return textField;
	}

    /**
     * Creates a check box.
     *
     * @param textId the id of the check box' text.
     * @param checked the initial state of the check box.
     * @return the check box.
     */
    public JCheckBox createCheckBox( String textId, boolean checked )
    {
        JCheckBox checkBox = new JCheckBox(
            textResource.getString(textId), checked );
        return checkBox;
    }

	/**
	 * Creates a <tt>JMenu</tt> with a specified string as label.
	 * @param id the id of the string.
	 * @param keyEvent the key event assoicated with the mnemonic.
	 * @return the menu.
	 */
	public JMenu createMenu( String id )
	{
		JMenu result = new JMenu( textResource.getString(id) );
		int mnemonic = getMnemonic(id);
		if (mnemonic != -1)
		{
			result.setMnemonic(mnemonic);
		}
		return result;
	}

	/**
	 * Creates a <tt>JMenuItem</tt> with a specified string as label.
	 * @param id the id of the string.
	 * @param l the action listener that has to be added to the menu item or
	 *          <tt>null</tt> if no listener has to be added.
	 * @return the menu item.
	 */
	public JMenuItem createMenuItem( String id, ActionListener l )
	{
		JMenuItem result = new JMenuItem( textResource.getString(id) );
		result.setActionCommand(id);
		int mnemonic = getMnemonic(id);
		if (mnemonic != -1)
		{
		    result.setMnemonic(mnemonic);
		}
		String keyStrokeDescription = textResource.getString(id + ".keystroke");
		if (keyStrokeDescription != null)
		{
			KeyStroke keyStroke = KeyStroke.getKeyStroke(keyStrokeDescription);
			if (keyStroke != null)
			{
				result.setAccelerator(keyStroke);
			}
		}
		if (l != null)
		{
			result.addActionListener(l);
		}
		return result;
	}

    /**
     * Creates a <tt>JComboBox</tt> with the specified values.
     * @param ids the ids of the values.
     * @return the <tt>JComboBox</tt>.
     */
    public JComboBox createComboBox( String[] ids )
    {
        JComboBox result = new JComboBox();
        for (int i = 0; i < ids.length; i++)
        {
            result.addItem(textResource.getString(ids[i]));
        }
        return result;
    }

    /**
     * Creates an icon. Icons are cached.
     * @param id the id of a string resource. The string resource refers to an image resource
     * @return the icon or <code>null</code> if no icon exists with the specified id
     */
    public Icon createIcon(String id) {
    	Icon icon = IconCache.getInstance().getIcon(id);
    	if (icon == null) {
            TextResource tr = TextResource.getInstance();
            String iconResourceName = tr.getString(id);
            if (iconResourceName == null) {
                return null;
            }

            URL iconUrl = WidgetFactory.class.getResource(iconResourceName);
            if (iconUrl == null) {
                return null;
            }

            String description = tr.getString(id + ".description");
            if (description != null) {
                icon = new ImageIcon(iconUrl, description);
            } else {
                icon = new ImageIcon(iconUrl);
            }
            IconCache.getInstance().addIcon(id, icon);
    	}
        return icon;
    }

    /**
     * Creates a table component that allows the user to sort its rows.
     * @param tableModel the table model
     * @return the table component
     */
    public SortedTable createSortedTable(SortedTableModel tableModel) {
        return new SortedTableImpl(tableModel, false, JTable.AUTO_RESIZE_ALL_COLUMNS);
    }

    /**
     * Creates a table component that allows the user to sort its rows.
     * @param tableModel the table model
     * @param autoResizeMode the auto resize mode of the table
     * @return the table component
     */
    public SortedTable createSortedTable(SortedTableModel tableModel, int autoResizeMode) {
        return new SortedTableImpl(tableModel, false, autoResizeMode);
    }

    /**
     * Creates a table component that does not allow the user to sort its rows.
     * @param tableModel the table model
     * @return the table component
     */
    public SortedTable createUnsortedTable(SortedTableModel tableModel) {
        return new SortedTableImpl(tableModel, true, JTable.AUTO_RESIZE_ALL_COLUMNS);
    }

    /**
     * Creates a table component that does not allow the user to sort its rows.
     * @param tableModel the table model
     * @param autoResizeMode the auto resize mode of the table
     * @return the table component
     */
    public SortedTable createUnsortedTable(SortedTableModel tableModel, int autoResizeMode) {
        return new SortedTableImpl(tableModel, true, autoResizeMode);
    }

    /**
     * Gets the mnemonic for the specified resource.
     * @param id the id of the resource
     * @return the mnemonic or -1 if no mnemonic was found.
     */
    private int getMnemonic(String id)
    {
        int result = -1;
		try
		{
			Class clazz = Class.forName("java.awt.event.KeyEvent");
		    Field field = clazz.getField(textResource.getString(id + ".mnemonic"));
			result = field.getInt(null);
		}
		catch (Exception e)
		{
		    // no mnemonic available in the resources.
		}
		return result;
    }
}
