/*
 * $Id: WidgetFactory.java,v 1.8 2007-11-11 19:48:53 sanderk Exp $
 *
 * Copyright (C) 2005 Sander Kooijmans
 *
 */

package nl.gogognome.swing;

import java.awt.event.*;
import java.lang.reflect.Field;
import java.net.URL;

import javax.swing.*;

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
    
    public Icon createIcon(String id) {
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
            return new ImageIcon(iconUrl, description);
        } else {
            return new ImageIcon(iconUrl);
        }
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
