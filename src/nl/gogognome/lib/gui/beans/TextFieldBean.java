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
package nl.gogognome.lib.gui.beans;

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

import nl.gogognome.lib.gui.Deinitializable;
import nl.gogognome.lib.swing.SwingUtils;
import nl.gogognome.lib.swing.models.AbstractModel;
import nl.gogognome.lib.swing.models.ModelChangeListener;
import nl.gogognome.lib.swing.models.StringModel;

/**
 * This class implements a bean for entering a <code>String</code>.
 *
 * @author Sander Kooijmans
 */
public class TextFieldBean extends JPanel implements Deinitializable {

    /** The model that stores the string of this bean. */
    private StringModel stringModel;

    /** The text field in which the user can enter the string. */
    private JTextField textfield;

    /** The model change listener for the string model. */
    private ModelChangeListener stringModelChangeListener;

    /** The document listener for the text field. */
    private DocumentListener documentListener;

    /**
     * Constructor.
     * @param stringModel the string model that will reflect the content of the bean
     */
    public TextFieldBean(StringModel stringModel) {
        this.stringModel = stringModel;

        setOpaque(false);

        setLayout(new GridBagLayout());

        textfield = new JTextField();

        updateTextField();
        stringModelChangeListener = new ModelChangeListener() {

            @Override
			public void modelChanged(AbstractModel model) {
                updateTextField();
            }

        };
        stringModel.addModelChangeListener(stringModelChangeListener);

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
    @Override
	public void addFocusListener(FocusListener listener) {
        textfield.addFocusListener(listener);
    }

    /**
     * @see JComponent#removeFocusListener(FocusListener)
     */
    @Override
	public void removeFocusListener(FocusListener listener) {
        textfield.removeFocusListener(listener);
    }

    /**
     * Updates the text field with the value of the string model.
     */
    private void updateTextField() {
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
