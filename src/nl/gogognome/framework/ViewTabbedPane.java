/*
 * $Id: ViewTabbedPane.java,v 1.2 2007-05-15 17:44:13 sanderk Exp $
 *
 * Copyright (C) 2006 Sander Kooijmans
 */
package nl.gogognome.framework;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;

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
    public void openView(final View view) {
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
    public void closeView(View view) {
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
    
    /** Closes all views in the tabbed pane. */
    public void closeAllViews() {
        for (Iterator iter = views.iterator(); iter.hasNext();) {
            View view = (View) iter.next();
            view.onClose();
            remove(view);
        }
        views.clear();
    }
}
