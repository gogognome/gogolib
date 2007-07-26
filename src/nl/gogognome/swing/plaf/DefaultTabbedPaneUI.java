/*
 * $Id: DefaultTabbedPaneUI.java,v 1.1 2007-07-26 19:07:57 sanderk Exp $
 *
 * Copyright (C) 2005 Sander Kooijmans
 *
 */

package nl.gogognome.swing.plaf;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

/**
 * Default UI for tabbed panes.
 */
public class DefaultTabbedPaneUI extends BasicTabbedPaneUI {

    private final static DefaultTabbedPaneUI INSTANCE = new DefaultTabbedPaneUI();
    
    public static ComponentUI createUI(JComponent c) {
        return INSTANCE;
    }
    
    public void installUI(JComponent c) {
        JTabbedPane tabbedPane = (JTabbedPane)c;
        super.installUI(tabbedPane);
        tabbedPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    }
    
}
