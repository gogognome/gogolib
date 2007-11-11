/*
 * $Id: DefaultTableUI.java,v 1.1 2007-11-11 19:49:23 sanderk Exp $
 *
 * Copyright (C) 2005 Sander Kooijmans
 *
 */

package nl.gogognome.swing.plaf;

import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTableUI;

import sun.swing.DefaultLookup;

/**
 * The default look and feel for a table. 
 */
public class DefaultTableUI extends BasicTableUI {
    
    public static ComponentUI createUI(JComponent c) {
        return new DefaultTableUI();
    }

    protected void installKeyboardActions() {
        super.installKeyboardActions();
        InputMap inputMap = (InputMap)DefaultLookup.get(table, this, "Table.ancestorInputMap"); 
        inputMap.remove(KeyStroke.getKeyStroke("ENTER"));
    }
}
