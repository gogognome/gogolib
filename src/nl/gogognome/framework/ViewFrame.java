/*
 * $Id: ViewFrame.java,v 1.1 2007-09-04 19:00:48 sanderk Exp $
 *
 * Copyright (C) 2005 Sander Kooijmans
 *
 */

package nl.gogognome.framework;

import javax.swing.JFrame;

/**
 * This class implements a view in a frame. 
 */
public class ViewFrame {

    /** The frame containing the view. */
    private JFrame frame = new JFrame();
    
    /** The view currently shown in the frame. */
    private View view;
    
    public ViewFrame(View view) {
        setView(view);
    }
    
    /**
     * Sets the view in the frame. If the frame already has a view,
     * then the old view will be closed and replaced by the new view.
     * @param view the view
     */
    public void setView(View view) {
        if (frame == null) {
            throw new IllegalStateException("This ViewFrame has been disposed.");
        }
        removeView();
        
        this.view = view;
        view.doInit();
        frame.getContentPane().add(view);
        if (frame.isVisible()) {
            frame.pack();
        }
    }

    /** Shows the frame. */
    public void showFrame() {
        if (frame == null) {
            throw new IllegalStateException("This ViewFrame has been disposed.");
        }
        frame.pack();
        frame.setVisible(true);
    }
    
    /** 
     * Disposes the frame. If it contains a view, the view will be closed too.
     * After calling this method, this instance should not be used anymore. 
     */
    public void dispose() {
        if (frame == null) {
            throw new IllegalStateException("This ViewFrame has been disposed.");
        }
        removeView();
        frame.dispose();
        frame = null;
    }
    
    /** Removes the view from the frame. */
    private void removeView() {
        if (frame == null) {
            throw new IllegalStateException("This ViewFrame has been disposed.");
        }
        View oldView = view;
        view = null;
        if (oldView != null) {
            oldView.doClose();
            frame.getContentPane().remove(oldView);
        }
    }
}
