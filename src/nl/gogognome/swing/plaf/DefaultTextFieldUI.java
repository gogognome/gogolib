/*
 * $Id: DefaultTextFieldUI.java,v 1.2 2007-09-15 18:58:21 sanderk Exp $
 *
 * Copyright (C) 2005 Sander Kooijmans
 *
 */

package nl.gogognome.swing.plaf;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTextFieldUI;

/**
 * Default UI for a text field.
 */
public class DefaultTextFieldUI extends BasicTextFieldUI {

    private FocusListener focusListener;
    
    public static ComponentUI createUI(JComponent c) {
        return new DefaultTextFieldUI();
    }

    public void installUI(JComponent c) {
        super.installUI(c);
        c.setFocusable(true);
        
        final JTextField textField = (JTextField) c;
        focusListener = new FocusListener() {

            public void focusGained(FocusEvent e) {
                textField.selectAll();
            }

            public void focusLost(FocusEvent e) {
                textField.select(0, 0);
            }
            
        };
        c.addFocusListener(focusListener);
    }
    
    public void uninstallUI(JComponent c) {
        c.removeFocusListener(focusListener);
        super.uninstallUI(c);
    }
}
