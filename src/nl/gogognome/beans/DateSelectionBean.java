/*
 * $Id: DateSelectionBean.java,v 1.2 2007-05-15 17:43:47 sanderk Exp $
 *
 * Copyright (C) 2006 Sander Kooijmans
 */
package nl.gogognome.beans;

import java.awt.Color;
import java.awt.FlowLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import nl.gogognome.framework.models.AbstractModel;
import nl.gogognome.framework.models.DateModel;
import nl.gogognome.framework.models.ModelChangeListener;
import nl.gogognome.text.TextResource;
import nl.gogognome.util.DateUtil;

/**
 * This class implements a bean for selecting a <code>Date</code>.
 *
 * @author Sander Kooijmans
 */
public class DateSelectionBean extends JPanel {
    
    /** The model that stores the date of this bean. */
    private DateModel dateModel;

    /** The text field in which the user can enter the date. */
    private JTextField tfDate;
    
    /** The date format used to format and parse dates. */
    private SimpleDateFormat dateFormat;
    
    /** The model change listener for the date model. */
    private ModelChangeListener dateModelChangeListener;
    
    /** The document listener for the text field. */
    private DocumentListener documentListener;
    
    /**
     * Constructor.
     * @param initialDate the date that is shown in the bean initially.
     */
    public DateSelectionBean(DateModel dateModel) {
        this.dateModel = dateModel;

        dateFormat = new SimpleDateFormat(TextResource.getInstance().getString("gen.dateFormat"));
        dateFormat.setLenient(false);
        
        setLayout(new FlowLayout());
        
        tfDate = new JTextField(10);

        updateTextField();
        dateModelChangeListener = new ModelChangeListener() {

            public void modelChanged(AbstractModel model) {
                updateTextField();
            }
            
        };
        dateModel.addModelChangeListener(dateModelChangeListener);
        
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
        
        tfDate.getDocument().addDocumentListener(documentListener);
        add(tfDate);
    }

    /**
     * Deinitializes this bean. It will free its resources.
     */
    public void deinitialize() {
        dateModel.removeModelChangeListener(dateModelChangeListener);
        tfDate.getDocument().removeDocumentListener(documentListener);
        dateModelChangeListener = null;
        documentListener = null;
        dateModel = null;
        dateFormat = null;
        tfDate = null;
    }
    
    /**
     * Updates the text field with the value of the date model.
     */
    private void updateTextField() {
        Date date = dateModel.getDate();
        if (date != null) {
            tfDate.setText(dateFormat.format(date));
        } else {
            tfDate.setText("");
        }
    }
    
    /**
     * Parses the date that is entered by the user. If the entered text is a valid
     * date, then the date model is updated.
     */
    private void parseUserInput() {
        try {
            Date date = dateFormat.parse(tfDate.getText());
            if (DateUtil.getField(date, Calendar.YEAR) < 1900) {
                throw new ParseException("Year is smaller than 1900", 0);
            }
            dateModel.setDate(date, dateModelChangeListener);
            tfDate.setBorder(null);
        } catch (ParseException ignore) {
            if (tfDate.getText().length() > 0) {
                tfDate.setBorder(new LineBorder(Color.RED));
            } else {
                tfDate.setBorder(null);
            }
        }
    }
}
