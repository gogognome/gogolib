/*
 * $Id: DefaultTextFieldUI.java,v 1.1 2007-09-09 19:33:20 sanderk Exp $
 *
 * Copyright (C) 2005 Sander Kooijmans
 *
 */

package nl.gogognome.swing.plaf;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTextFieldUI;

/**
 * Default UI for a text field.
 */
public class DefaultTextFieldUI extends BasicTextFieldUI {

    public static ComponentUI createUI(JComponent c) {
        return new DefaultTextFieldUI();
    }

    public void installUI(JComponent c) {
        super.installUI(c);
        c.setFocusable(true);
    }
}
