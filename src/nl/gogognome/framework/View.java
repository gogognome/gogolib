/*
 * $Id: View.java,v 1.3 2007-09-02 18:23:48 sanderk Exp $
 *
 * Copyright (C) 2006 Sander Kooijmans
 */
package nl.gogognome.framework;

import java.awt.Dialog;
import java.awt.Frame;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.JPanel;

/**
 * A view can be shown in a dialog or in a frame.
 *
 * @author Sander Kooijmans
 */
public abstract class View extends JPanel {

    /** The subscribed listeners. */
    private ArrayList listeners = new ArrayList();
    
    /** 
     * This action closes the view. The action will be set before the view
     * is shown. The view can use it let itself be closed.
     */
    protected Action closeAction;
    
    /** The parent frame that contains this view. */
    private Frame parentFrame;
    
    /** The parent dialog that contains this view. */
    private Dialog parentDialog;
    
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
    
    public void setParentFrame(Frame frame) {
        parentFrame = frame;
    }

    public Frame getParentFrame() {
        return parentFrame;
    }
    
    public void setParentDialog(Dialog dialog) {
        parentDialog = dialog;
    }

    public Dialog getParentDialog() {
        return parentDialog;
    }
    
    public void addViewListener(ViewListener listener) {
        listeners.add(listener);
    }
    
    public void removeViewListener(ViewListener listener) {
        listeners.remove(listener);
    }
    
    /**
     * Closes the view and notifies listeners.
     */
    void doClose() {
        onClose();
        ViewListener[] tempListeners = (ViewListener[]) listeners.toArray(new ViewListener[listeners.size()]);
        for (int i = 0; i < tempListeners.length; i++) {
            tempListeners[i].onViewClosed(this);
        }
    }
}
