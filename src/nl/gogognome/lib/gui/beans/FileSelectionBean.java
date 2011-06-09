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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusListener;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import nl.gogognome.lib.gui.Deinitializable;
import nl.gogognome.lib.swing.SwingUtils;
import nl.gogognome.lib.swing.WidgetFactory;
import nl.gogognome.lib.swing.models.AbstractModel;
import nl.gogognome.lib.swing.models.FileSelectionModel;
import nl.gogognome.lib.swing.models.ModelChangeListener;

/**
 * This class implements a bean for selecting a file.
 *
 * @author Sander Kooijmans
 */
public class FileSelectionBean extends JPanel implements Deinitializable {

    /** The model that stores the selected file. */
    private FileSelectionModel fileSelectionModel;

    /** The text field in which the user can enter the string. */
    private JTextField textfield;

    /** The model change listener for the file selection model. */
    private ModelChangeListener fileSelectionModelChangeListener;

    /** The document listener for the text field. */
    private DocumentListener documentListener;

    /** File name extension filter used when a file is selected using the dialog. */
    private FileNameExtensionFilter filter;

    private JButton openFileChooserButton;

    /**
     * Constructor.
     * @param fileSelectionModel the file selection model that will reflect the content of the bean
     */
    public FileSelectionBean(FileSelectionModel fileSelectionModel) {
        this.fileSelectionModel = fileSelectionModel;

        setOpaque(false);

        setLayout(new GridBagLayout());

        textfield = new JTextField();

        fileSelectionModelChangeListener = new ModelChangeListener() {

            @Override
			public void modelChanged(AbstractModel model) {
                updateTextField();
            }

        };
        fileSelectionModel.addModelChangeListener(fileSelectionModelChangeListener);

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
            GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
            0, 0, 0, 0));

        Action action = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleSelectFileWithDialog();
			}
		};
		openFileChooserButton = WidgetFactory.getInstance().createIconButton("gen.btnChooseFile", action, 21);
        add(openFileChooserButton, SwingUtils.createGBConstraints(1,0, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                0, 0, 0, 0));

        updateTextField();
    }

    /**
     * Deinitializes this bean. It will free its resources.
     */
    @Override
	public void deinitialize() {
        fileSelectionModel.removeModelChangeListener(fileSelectionModelChangeListener);
        textfield.getDocument().removeDocumentListener(documentListener);
        fileSelectionModelChangeListener = null;
        documentListener = null;
        fileSelectionModel = null;
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
     * Upstrings the text field with the value of the string model.
     */
    private void updateTextField() {
    	File f = fileSelectionModel.getFile();
        String string = f != null ? f.getAbsolutePath() : "";
        textfield.setText(string);
        textfield.setEnabled(fileSelectionModel.isEnabled());
        openFileChooserButton.setEnabled(fileSelectionModel.isEnabled());
    }

    /**
     * Parses the string that is entered by the user. If the entered text is a valid
     * string, then the string model is upstringd.
     */
    private void parseUserInput() {
        String string = textfield.getText();
        fileSelectionModel.setFile(new File(string), fileSelectionModelChangeListener);
    }

    /**
     * Lets the user select a file using a file chooser dialog.
     */
    private void handleSelectFileWithDialog() {
        JFileChooser chooser = new JFileChooser();
        if (filter != null) {
        	chooser.setFileFilter(filter);
        }

        int returnVal = chooser.showOpenDialog(getTopLevelAncestor());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
        	fileSelectionModel.setFile(chooser.getSelectedFile(), null);
        }
    }
}
