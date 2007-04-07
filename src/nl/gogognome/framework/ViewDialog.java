/*
 * $Id: ViewDialog.java,v 1.1 2007-04-07 15:23:47 sanderk Exp $
 *
 * Copyright (C) 2006 Sander Kooijmans
 */
package nl.gogognome.framework;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDialog;

/**
 * This class implements a <code>JDialog</code> that can hold a <code>View</code>.
 *
 * @author Sander Kooijmans
 */
public class ViewDialog extends JDialog {

    /** The view shown in this dialog. */
    private View view;
    
    /**
     * Constructor.
     * @param owner the owner of this dialog
     * @param view the view to be shown in this dialog
     */
    public ViewDialog(Dialog owner, View view) {
        super(owner, view.getTitle());
        setView(view);
    }
    
    /**
     * Constructor.
     * @param owner the owner of this dialog
     * @param view the view to be shown in this dialog
     */
    public ViewDialog(Frame owner, View view) {
        super(owner, view.getTitle());
        setView(view);
    }
    
    /**
     * Sets the view on this dialog.
     * @param view the view
     */
    private void setView(View view) {
        this.view = view;
        Action closeAction = new AbstractAction() {
            public void actionPerformed(ActionEvent event) {
                dispose();
            }
            
        };
        
        view.setCloseAction(closeAction);
        view.onInit();
        getContentPane().add(view, BorderLayout.CENTER);
    }
    
    /** Disposes the dialog. */
    public void dispose() {
        view.onClose();
        super.dispose();
    }
}
