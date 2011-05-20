/*
    This file is part of gogolib.

    gogolib is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    gogolib is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with gogolib.  If not, see <http://www.gnu.org/licenses/>.
*/
package nl.gogognome.beans;

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
