/*
 * $Id: DateSelectionBean.java,v 1.4 2007-09-09 19:32:00 sanderk Exp $
 *
 * Copyright (C) 2006 Sander Kooijmans
 */
package nl.gogognome.beans;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;

import nl.gogognome.framework.models.AbstractModel;
import nl.gogognome.framework.models.DateModel;
import nl.gogognome.framework.models.ModelChangeListener;
import nl.gogognome.swing.SwingUtils;
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

        setOpaque(false);
        
        dateFormat = new SimpleDateFormat(TextResource.getInstance().getString("gen.dateFormat"));
        dateFormat.setLenient(false);
        
        setLayout(new GridBagLayout());
        
        tfDate = new JTextField(10);

        updateTextField();
        dateModelChangeListener = new ModelChangeListener() {

            public void modelChanged(AbstractModel model) {
                updateTextField();
            }
            
        };
        dateModel.addModelChangeListener(dateModelChangeListener);
        
        tfDate.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent input) {
                return parseInput(tfDate.getText());
            }
        });
        add(tfDate, SwingUtils.createGBConstraints(0,0, 1, 1, 1.0, 0.0, 
            GridBagConstraints.WEST, GridBagConstraints.NONE,
            0, 0, 0, 0));
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
     * @param input the user input
     * @return <code>true</code> if the input represents a valid date or if the input is empty;
     *         <code>false</code> otherwise
     */
    private boolean parseInput(String input) {
        if (input == null || input.length() == 0) {
            dateModel.setDate(null, dateModelChangeListener);
            return true;
        }
        try {
            Date date = dateFormat.parse(input);
            if (DateUtil.getField(date, Calendar.YEAR) < 1900) {
                throw new ParseException("Year is smaller than 1900", 0);
            }
            dateModel.setDate(date, dateModelChangeListener);
            tfDate.setBorder(null);
            return true;
        } catch (ParseException ignore) {
            return false;
        }
    }
}
