/*
   Copyright 2011 Sander Kooijmans

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package nl.gogognome.lib.gui.beans;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import nl.gogognome.lib.gui.Deinitializable;
import nl.gogognome.lib.swing.SwingUtils;
import nl.gogognome.lib.swing.models.AbstractModel;
import nl.gogognome.lib.swing.models.DateModel;
import nl.gogognome.lib.swing.models.ModelChangeListener;
import nl.gogognome.lib.text.TextResource;
import nl.gogognome.lib.util.DateUtil;

/**
 * This class implements a bean for selecting a <code>Date</code>.
 *
 * @author Sander Kooijmans
 */
public class DateSelectionBean extends JPanel implements Deinitializable {

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
     * @param dateModel the date model that will reflect the content of the bean
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

            @Override
			public void modelChanged(AbstractModel model) {
                updateTextField();
            }

        };
        dateModel.addModelChangeListener(dateModelChangeListener);

        documentListener = new DocumentListener() {

            @Override
			public void changedUpdate(DocumentEvent evt) {
                parseUserInput();
            }

            @Override
			public void insertUpdate(DocumentEvent evt) {
                parseUserInput();
            }

            @Override
			public void removeUpdate(DocumentEvent evt) {
                parseUserInput();
            }
        };

        tfDate.getDocument().addDocumentListener(documentListener);
        add(tfDate, SwingUtils.createGBConstraints(0,0, 1, 1, 1.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.NONE,
            0, 0, 0, 0));    }

    /**
     * Deinitializes this bean. It will free its resources.
     */
    @Override
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
     * @see JComponent#addFocusListener(FocusListener)
     */
    @Override
    public void addFocusListener(FocusListener listener) {
        tfDate.addFocusListener(listener);
    }

    /**
     * @see JComponent#removeFocusListener(FocusListener)
     */
    @Override
    public void removeFocusListener(FocusListener listener) {
        tfDate.removeFocusListener(listener);
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
            tfDate.setBorder(new LineBorder(Color.GRAY));
        } catch (ParseException ignore) {
            if (dateModel.getDate() != null) {
                dateModel.setDate(null, dateModelChangeListener);
            }
            if (tfDate.getText().length() > 0) {
                tfDate.setBorder(new LineBorder(Color.RED));
            } else {
                tfDate.setBorder(new LineBorder(Color.GRAY));
            }
        }
    }
}
