/*
   Copyright 2011 Sander Kooijmans

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package nl.gogognome.lib.swing;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
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
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import nl.gogognome.lib.text.TextResource;

/**
 * This class is a factory for buttons, menus, menu items, text fields and
 * check boxes.
 *
 * @author kooijmas
 */
public class WidgetFactory {

	private final static Logger LOGGER = Logger.getLogger(WidgetFactory.class.getName());

	/** The singleton instance of this class. */
	private static WidgetFactory instance;

	/** The <code>TextResource</code> used to obtain string resources. */
	private TextResource textResource;

	private Map<String, Icon> iconCache = new HashMap<String, Icon>();
	private Map<String, Image> imageCache = new HashMap<String, Image>();

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
	 * @param the action to be performed when the button is pressed
	 * @return the button.
	 */
	public JButton createButton(String id, Action action) {
	    ActionWrapper actionWrapper = createAction(id);
	    actionWrapper.setAction(action);

		return new JButton(actionWrapper);
	}

	/**
	 * Creates a button containing an icon only.
	 * @param id the id of the button's description in the resources.
	 * @param the action to be performed when the button is pressed
	 * @param size the width and height in pixels
	 * @return the button.
	 */
	public JButton createIconButton(String id, Action action, int size) {
	    JButton button = createButton(id, action);
	    button.setText(null);
	    button.setPreferredSize(new Dimension(size, size));
		return button;
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
     * Creates an icon.
     * The id specifies a string resource. The string represents the path to the image.
     *
     * <p>Icons are cached.
     * @param id the id of a string resource. The string resource refers to an image resource
     * @return the icon or <code>null</code> if no icon exists with the specified id
     */
    public Icon createIcon(String id) {
    	Icon icon = iconCache.get(id);
    	if (icon == null) {
            URL iconUrl = getUrlForResource(id);
            if (iconUrl == null) {
                return null;
            }

            String description = TextResource.getInstance().getString(id + ".description");
            if (description != null) {
                icon = new ImageIcon(iconUrl, description);
            } else {
                icon = new ImageIcon(iconUrl);
            }
            iconCache.put(id, icon);
    	}
        return icon;
    }

    /**
     * Creates an image.
     * The id specifies a string resource. The string represents the path to the image.
     *
     * <p>Images are cached.
     * @param id the id of a string resource. The string resource refers to an image resource
     * @return the icon or <code>null</code> if no icon exists with the specified id
     */
    public Image createImage(String id) {
    	Image image = imageCache.get(id);
    	if (image == null) {
            URL imageUrl = getUrlForResource(id);
            if (imageUrl == null) {
                return null;
            }

            try {
				image = ImageIO.read(imageUrl);
			} catch (IOException e) {
				LOGGER.log(Level.WARNING, "Failed to load image " + imageUrl + ": " + e.getMessage(), e);
			}
            imageCache.put(id, image);
    	}
        return image;
    }

    /**
     * Gets the URL that represents a resource (e.g., an image).
     * The id specifies a string resource. The string represents the path to the actual resource
     * (e.g., an image).
     * @param id the id of the resource
     * @return the URL
     */
    private URL getUrlForResource(String id) {
        TextResource tr = TextResource.getInstance();
        String resourceName = tr.getString(id);
        if (resourceName == null) {
            return null;
        } else {
        	return getClass().getResource(resourceName);
        }
    }

    /**
     * Creates a title border for the specified title.
     * @param titleId the id of the title
     * @return the title border
     */
    public Border createTitleBorder(String titleId) {
    	return new CompoundBorder(
                new TitledBorder(' '  + textResource.getString(titleId) + ' '),
                new EmptyBorder(10, 10, 10, 10));
    }

    /**
     * Creates a title border for the specified title with extra margin.
     * @param titleId the id of the title
     * @return the title border
     */
    public Border createTitleBorderWithMargin(String titleId) {
    	return new CompoundBorder(new EmptyBorder(10, 10, 10, 10),
    		createTitleBorder(titleId));
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
			String mnemonicId = id + ".mnemonic";
			if (textResource.containsString(mnemonicId)) {
				Class<?> clazz = KeyEvent.class;
			    Field field = clazz.getField(textResource.getString(mnemonicId));
				result = field.getInt(null);
			}
		}
		catch (Exception e)
		{
		    // no mnemonic available in the resources.
		}
		return result;
    }
}
