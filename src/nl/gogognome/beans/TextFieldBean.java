/*
 * $Id: TextFieldBean.java,v 1.1 2009-01-03 12:21:15 sanderk Exp $
 *
 * Copyright (C) 2006 Sander Kooijmans
 */
package nl.gogognome.beans;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusListener;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import nl.gogognome.framework.models.AbstractModel;
import nl.gogognome.framework.models.ModelChangeListener;
import nl.gogognome.framework.models.StringModel;
import nl.gogognome.swing.SwingUtils;

/**
 * This class implements a bean for entering a <code>String</code>.
 *
 * @author Sander Kooijmans
 */
public class TextFieldBean extends JPanel {
    
    /** The model that stores the string of this bean. */
    private StringModel stringModel;

    /** The text field in which the user can enter the string. */
    private JTextField textfield;
    
    /** The model change listener for the stirng model. */
    private ModelChangeListener stringModelChangeListener;
    
    /** The document listener for the text field. */
    private DocumentListener documentListener;
    
    /**
     * Constructor.
     * @param stringModel the stirng model that will reflect the content of the bean
     */
    public TextFieldBean(StringModel stringModel) {
        this.stringModel = stringModel;

        setOpaque(false);
        
        setLayout(new GridBagLayout());
        
        textfield = new JTextField();

        upstringTextField();
        stringModelChangeListener = new ModelChangeListener() {

            public void modelChanged(AbstractModel model) {
                upstringTextField();
            }
            
        };
        stringModel.addModelChangeListener(stringModelChangeListener);
        
        documentListener = new DocumentListener() {

            public void changedUpdate(DocumentEvent evt) {
                parseUserInput();
            }

            public void insertUpdate(DocumentEvent evt) {
                parseUserInput();
            }

            public void removeUpdate(DocumentEvent evt) {
                parseUserInput();
            }
        };
        
        textfield.getDocument().addDocumentListener(documentListener);
        add(textfield, SwingUtils.createGBConstraints(0,0, 1, 1, 1.0, 0.0, 
            GridBagConstraints.WEST, GridBagConstraints.NONE,
            0, 0, 0, 0));    }

    /**
     * Deinitializes this bean. It will free its resources.
     */
    public void deinitialize() {
        stringModel.removeModelChangeListener(stringModelChangeListener);
        textfield.getDocument().removeDocumentListener(documentListener);
        stringModelChangeListener = null;
        documentListener = null;
        stringModel = null;
        textfield = null;
    }

    /**
     * @see JComponent#addFocusListener(FocusListener)
     */
    public void addFocusListener(FocusListener listener) {
        textfield.addFocusListener(listener);
    }
    
    /**
     * @see JComponent#removeFocusListener(FocusListener)
     */
    public void removeFocusListener(FocusListener listener) {
        textfield.removeFocusListener(listener);
    }
    
    /**
     * Upstrings the text field with the value of the string model.
     */
    private void upstringTextField() {
        String string = stringModel.getString();
        if (string != null) {
            textfield.setText(string);
        } else {
            textfield.setText("");
        }
    }
    
    /**
     * Parses the string that is entered by the user. If the entered text is a valid
     * string, then the string model is upstringd.
     */
    private void parseUserInput() {
//        try {
            String string = textfield.getText();
            stringModel.setString(string, stringModelChangeListener);
            textfield.setBorder(new LineBorder(Color.GRAY));
//        } catch (ParseException ignore) {
//            if (textfield.getText().length() > 0) {
//                textfield.setBorder(new LineBorder(Color.RED));
//            } else {
//                textfield.setBorder(new LineBorder(Color.GRAY));
//            }
//        }
    }
}
