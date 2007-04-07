/*
 * $Id: ViewTabbedPane.java,v 1.1 2007-04-07 15:23:47 sanderk Exp $
 *
 * Copyright (C) 2006 Sander Kooijmans
 */
package nl.gogognome.framework;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTabbedPane;

/**
 * This class implements a tabbed pane that can hold <code>View</code>s.
 *
 * @author Sander Kooijmans
 */
public class ViewTabbedPane extends JTabbedPane {

    /** Contains the views that are added to this tabbed pane. */
    private ArrayList views = new ArrayList();
    
    /**
     * Adds a view to the tabbed pane.
     * @param view the view to be added
     */
    public void addView(final View view) {
        Action closeAction = new AbstractAction() {
            public void actionPerformed(ActionEvent event) {
                remove(view);
            }
        };
        
        view.setCloseAction(closeAction);
        view.onInit();
        addTab(view.getTitle(), view);
        views.add(view);
    }

    /**
     * Removes a view from the tabbed pane.
     * @param view the view to be removed
     */
    public void removeView(View view) {
        if (views.contains(view)) {
            view.onClose();
            remove(view);
            views.remove(view);
        }
    }
    
    /**
     * Select the tab that contains the specified view.
     * @param view the view
     */
    public void selectView(View view) {
        int count = getTabCount();
        for (int i=0; i<count; i++) {
            if (getComponentAt(i).equals(view)) {
                setSelectedIndex(i);
                return;
            }
        }
    }
}
