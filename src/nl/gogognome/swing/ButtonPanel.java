/*
 * $Id: ButtonPanel.java,v 1.1 2007-09-15 18:56:20 sanderk Exp $
 *
 * Copyright (C) 2005 Sander Kooijmans
 *
 */

package nl.gogognome.swing;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * This class implements a panel that can hold a number of buttons.
 * The buttons are shown in a single row.
 * The location of the buttons within the panel is configurable.
 */
public class ButtonPanel extends JPanel {
    
    /** The location of the buttons within the panel. */
    private int location;
    
    /** A label that gets added to this panel to push the row of buttons to the left or the right. */
    private JLabel label = new JLabel();
    
    /**
     * Constructs a button panel.
     * @param location specifies the location of the buttons within the panel.
     *        Allowed values are <code>SwingConstants.LEFT</code>,
     *        <code>SwingConstants.CENTER</code> and <code>SwingConstants.RIGHT</code>
     */
    public ButtonPanel(int location) {
        super();
        this.location = location;
        switch(location) {
        case SwingConstants.CENTER:
            setLayout(new GridBagLayout());
            add(new JLabel(), SwingUtils.createGBConstraints(0, 0, 1, 1, 1.0, 1.0, 
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 0, 0, 0, 0));
            add(label, SwingUtils.createGBConstraints(1, 0, 1, 1, 1.0, 1.0, 
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 0, 0, 0, 0));
            break;
            
        case SwingConstants.LEFT:
            setLayout(new GridBagLayout());
            add(label, SwingUtils.createGBConstraints(0, 0, 1, 1, 1.0, 1.0, 
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 0, 0, 0, 0));
            break;
            
        case SwingConstants.RIGHT:
            setLayout(new GridBagLayout());
            add(label, SwingUtils.createGBConstraints(0, 0, 1, 1, 1.0, 1.0, 
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 0, 0, 0, 0));
            break;
        
        default:
            throw new IllegalArgumentException("Invalid location specified: " + location);
        }
    }
    
    /**
     * Adds a button to this panel.
     * @param button the button to be added
     */
    public Component add(Component button) {
        switch(location) {
        case SwingConstants.CENTER:
        case SwingConstants.LEFT:
            // Remove the label, add the button and add the label as right-most element in the row
            remove(label);
            add(button, SwingUtils.createGBConstraints(getComponentCount(), 0, 1, 1, 0.0, 0.0, 
                GridBagConstraints.CENTER, GridBagConstraints.NONE, 5, 5, 5, 5));
            add(label, SwingUtils.createGBConstraints(getComponentCount(), 0, 1, 1, 1.0, 1.0, 
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 0, 0, 0, 0));
            break;
            
        case SwingConstants.RIGHT:
            add(button, SwingUtils.createGBConstraints(getComponentCount(), 0, 1, 1, 0.0, 0.0, 
                GridBagConstraints.CENTER, GridBagConstraints.NONE, 5, 5, 5, 5));
            break;
        }
        return button;
    }
    
}
