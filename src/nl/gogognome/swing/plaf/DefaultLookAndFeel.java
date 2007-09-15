/*
 * $Id: DefaultLookAndFeel.java,v 1.3 2007-09-15 18:57:58 sanderk Exp $
 *
 * Copyright (C) 2005 Sander Kooijmans
 *
 */

package nl.gogognome.swing.plaf;

import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;

/**
 * This class implements the default look and feel for the
 * gogolib-based projects. 
 */
public class DefaultLookAndFeel extends MetalLookAndFeel {

    private static final long serialVersionUID = 1L;

    public static void useDefaultLookAndFeel() {
        try {
//            UIManager.setLookAndFeel(new SubstanceLookAndFeel());
            UIManager.setLookAndFeel(new DefaultLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
            // Don't let application crash because of an unsupported look and feel.
        }
    }
    
    /* (non-Javadoc)
     * @see javax.swing.LookAndFeel#getDescription()
     */
    public String getDescription() {
        return "The gogolib look and feel";
    }

    /* (non-Javadoc)
     * @see javax.swing.LookAndFeel#getID()
     */
    public String getID() {
        return "gogolib";
    }

    /* (non-Javadoc)
     * @see javax.swing.LookAndFeel#getName()
     */
    public String getName() {
        return "The gogolib look and feel";
    }

    /* (non-Javadoc)
     * @see javax.swing.LookAndFeel#isNativeLookAndFeel()
     */
    public boolean isNativeLookAndFeel() {
        return false;
    }

    /* (non-Javadoc)
     * @see javax.swing.LookAndFeel#isSupportedLookAndFeel()
     */
    public boolean isSupportedLookAndFeel() {
        return true;
    }

    /**
     * This method is called once by <code>UIManager.setLookAndFeel</code> to create 
     * the look and feel specific defaults table. Other applications, for example 
     * an application builder, may also call this method.
     */
    public UIDefaults getDefaults() {
        UIDefaults defaults = super.getDefaults();
        defaults.put("TextFieldUI", DefaultTextFieldUI.class.getName());
        defaults.put("TabbedPaneUI", DefaultTabbedPaneUI.class.getName());
        return defaults;
    }
}
