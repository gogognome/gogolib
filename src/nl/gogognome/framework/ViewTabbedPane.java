/*
 * $Id: ViewTabbedPane.java,v 1.4 2007-09-02 19:49:04 sanderk Exp $
 *
 * Copyright (C) 2006 Sander Kooijmans
 */
package nl.gogognome.framework;

import java.awt.Frame;
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
    
    /** The frame that contains this tabbed pane. */
    private Frame parentFrame;
    
    /**
     * Constructor.
     * @param parentFrame the frame that contains this tabbed pane.
     */
    public ViewTabbedPane(Frame parentFrame) {
        super();
        this.parentFrame = parentFrame;
    }
    
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
        view.setParentFrame(parentFrame);
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
            view.doClose();
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
            view.doClose();
            remove(view);
        }
        views.clear();
    }

    /**
     * Checks whether the specified view is present in this tabbed pane.
     * @param view the view
     * @return <code>true</code> if the specified view is present;
     *         <code>false</code> otherwise
     */
    public boolean hasView(View view) {
        int count = getTabCount();
        for (int i=0; i<count; i++) {
            if (getComponentAt(i).equals(view)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * This method can be called by the UI when the user closes the tab.
     * @param index index of the tab to be closed
     */
    public void remove(int index) {
        View view = (View)getComponentAt(index); 
        view.doClose();
        views.remove(view);
        super.remove(index);
    }
}
