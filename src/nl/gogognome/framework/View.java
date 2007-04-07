/*
 * $Id: View.java,v 1.1 2007-04-07 15:23:47 sanderk Exp $
 *
 * Copyright (C) 2006 Sander Kooijmans
 */
package nl.gogognome.framework;

import javax.swing.Action;
import javax.swing.JPanel;

/**
 * A view can be shown in a dialog or in a frame.
 *
 * @author Sander Kooijmans
 */
public abstract class View extends JPanel {

    /** 
     * This action closes the view. The action will be set before the view
     * is shown. The view can use it let itself be closed.
     */
    protected Action closeAction;
    
    /**
     * Gets the title of the view.
     * @return the title of the view
     */
    public abstract String getTitle();
    
    /** This method is called before the view is shown. It initializes the view. */
    public abstract void onInit();
    
    /** This method is called just before the view is closed. It can free resources. */ 
    public abstract void onClose();
    
    /** 
     * Sets the close action. 
     * 
     * @param closeAction the close action
     */
    public void setCloseAction(Action closeAction) {
        this.closeAction = closeAction;
    }
}
