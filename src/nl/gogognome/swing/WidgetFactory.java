/*
 * $Id: WidgetFactory.java,v 1.1 2006-11-20 18:41:08 sanderk Exp $
 *
 * Copyright (C) 2005 Sander Kooijmans
 *
 */

package nl.gogognome.swing;

import java.awt.event.*;
import java.lang.reflect.Field;

import javax.swing.*;

import nl.gogognome.text.TextResource;

/**
 * This class is a factory for buttons, menus, menu items, text fields and
 * check boxes.
 *  
 * @author kooijmas
 */
public class WidgetFactory 
{
	
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
	 * Creates a button.
	 * @param id the id of the button's text.
	 * @return the button.
	 */	
	public JButton createButton( String id ) 
	{
		JButton button = new JButton( textResource.getString(id) );
		button.setActionCommand(id);
		int mnemonic = getMnemonic(id);
		if (mnemonic != -1)
		{
			button.setMnemonic(mnemonic);
		}
	   
		return button;
	}

	/**
	 * Creates a label with the specified text.
	 * 
	 * @param id the id of the text.
	 * @return the label.
	 */	
	public JLabel createLabel(String id) 
	{
	    JLabel label = new JLabel(textResource.getString(id));
		return label;
	}
	
	/**
	 * Creates a text field. When the created text field gains focus, its
	 * contents is selected.
	 * 
	 * @return the text field.
	 */	
	public JTextField createTextField() 
	{
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
