/*
 * $Id: View.java,v 1.6 2007-11-11 18:57:53 sanderk Exp $
 *
 * Copyright (C) 2006 Sander Kooijmans
 */
package nl.gogognome.framework;

import java.awt.Container;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
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
    private JFrame parentFrame;
    
    /** The parent dialog that contains this view. */
    private JDialog parentDialog;
    
    /** The default button of this view. */
    private JButton defaultButton;
    
    /**
     * Gets the title of the view.
     * @return the title of the view
     */
    public abstract String getTitle();
    
    /**
     * Gets the default button of this view.
     * @return the default button of this view or <code>null</code> if this view
     *         has no default button
     */
    JButton getDefaultButton() {
        return defaultButton;
    }
    
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
    
    public void setParentFrame(JFrame frame) {
        parentFrame = frame;
    }

    /**
     * Returns the parent container of the view.
     * @return the parent container of the view
     */
    public Container getParent() {
        if (parentDialog != null) {
            return parentDialog;
        } else {
            return parentFrame;
        }
    }
    
    public JFrame getParentFrame() {
        return parentFrame;
    }
    
    public void setParentDialog(JDialog dialog) {
        parentDialog = dialog;
    }

    public JDialog getParentDialog() {
        return parentDialog;
    }
    
    public void addViewListener(ViewListener listener) {
        listeners.add(listener);
    }
    
    public void removeViewListener(ViewListener listener) {
        listeners.remove(listener);
    }
    
    /**
     * Sets the default button of this view.
     * @param button the default button
     */
    public void setDefaultButton(JButton button) {
        defaultButton = button;
        if (parentFrame != null) {
            parentFrame.getRootPane().setDefaultButton(button);
        } else if (parentDialog != null) {
            parentDialog.getRootPane().setDefaultButton(button);
        }
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
    
    /** Initializes the view. */
    void doInit() {
        onInit();
    }
}
