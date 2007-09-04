/*
 * $Id: ViewDialog.java,v 1.3 2007-09-04 19:00:29 sanderk Exp $
 *
 * Copyright (C) 2006 Sander Kooijmans
 */
package nl.gogognome.framework;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/**
 * This class implements a <code>JDialog</code> that can hold a <code>View</code>.
 *
 * @author Sander Kooijmans
 */
public class ViewDialog {

    /** The view shown in this dialog. */
    private View view;
    
    /** The actual dialog holding the view. */
    private JDialog dialog;
    
    /** 
     * The bounds of the parent frame or dialog at the moment this dialog
     * was created. 
     */
    private Rectangle parentBounds;

    /** The action to close this dialog. */
    private Action closeAction;
    
    /**
     * Constructor.
     * @param owner the owner of this dialog
     * @param view the view to be shown in this dialog
     */
    public ViewDialog(Dialog owner, View view) {
        initDialog(new JDialog(owner, view.getTitle(), true), view, owner.getBounds());
    }
    
    /**
     * Constructor.
     * @param owner the owner of this dialog
     * @param view the view to be shown in this dialog
     */
    public ViewDialog(Frame owner, View view) {
        initDialog(new JDialog(owner, view.getTitle(), true), view, owner.getBounds());
    }
    
    /**
     * Initializes the dialog
     * @param dialog the dialog
     * @param view the view to be shown in the dialog
     */
    private void initDialog(JDialog dialog, View view, Rectangle parentBounds) {
        this.dialog = dialog;
        this.parentBounds = parentBounds;
        setView(view);
        
        dialog.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent e) { dispose(); } }
        );

        dialog.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "ESC_ACTION");
        dialog.getRootPane().getActionMap().put("ESC_ACTION", closeAction);
    }
    
    /**
     * Shows the dialog.
     */
    final public void showDialog() {
        // Determine the currently focused component so the focus can be
        // returned to this component after the dialog has been shown.
        final Component focusedComponent = 
            KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
     
        // Show the dialog.
        dialog.pack();
        Dimension d = dialog.getPreferredSize();
        dialog.setLocation( parentBounds.x + (parentBounds.width-d.width)/2,
            parentBounds.y + (parentBounds.height-d.height)/2 );
        dialog.setVisible(true);
        
        // Give focus back to the component that had focus before this dialog was shown.
        if (focusedComponent != null) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    focusedComponent.requestFocus();
                }
            });
        }
    }

    /**
     * Sets the view on this dialog.
     * @param view the view
     */
    private void setView(View view) {
        this.view = view;
        closeAction = new AbstractAction() {
            public void actionPerformed(ActionEvent event) {
                dispose();
            }
        };
        
        view.setCloseAction(closeAction);
        view.doInit();
        dialog.getContentPane().add(view, BorderLayout.CENTER);
        
        JButton defaultButton = view.getDefaultButton();
        if (defaultButton != null) {
            dialog.getRootPane().setDefaultButton(view.getDefaultButton());
        }
    }
    
    /** Disposes the dialog. */
    public void dispose() {
        view.doClose();
        dialog.dispose();
    }
}
