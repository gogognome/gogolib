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

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nl.gogognome.lib.gui.Deinitializable;
import nl.gogognome.lib.swing.SwingUtils;
import nl.gogognome.lib.swing.WidgetFactory;
import nl.gogognome.lib.swing.models.BooleanModel;
import nl.gogognome.lib.swing.models.DateModel;
import nl.gogognome.lib.swing.models.DoubleModel;
import nl.gogognome.lib.swing.models.FileSelectionModel;
import nl.gogognome.lib.swing.models.ListModel;
import nl.gogognome.lib.swing.models.StringModel;

/**
 * This class implements a panel containing a column of input fields.
 * Each input field consists of a label and a component (typically a text field).
 * The values of the fields are managed by models (e.g., {@link StringModel}
 * or {@link DateModel}.
 */
public class ValuesEditPanel extends JPanel implements Deinitializable {

    private static final long serialVersionUID = 1L;

	/** Contains the number of fields present in the panel. */
    private int nrFields;

    private List<Component> components = new ArrayList<Component>();

    private BeanFactory beanFactory = BeanFactory.getInstance();

    /**
     * Constructor.
     */
    public ValuesEditPanel() {
        super(new GridBagLayout());
    }

    /**
     * This method should be called after the view containing this panel is closed.
     * It releases its resources.
     */
    @Override
	public void deinitialize() {
    	for (Component c : components) {
    		if (c instanceof Deinitializable) {
    			((Deinitializable) c).deinitialize();
    		}
    	}
    }

    /**
     * Adds a field to edit a string.
     * @param labelId the id of the label that is put in front of the text field
     * @param model the model controlling the text field
     */
    public void addField(String labelId, StringModel model) {
        addField(labelId, beanFactory.createTextFieldBean(model));
    }

    /**
     * Adds a field to edit a string.
     * @param labelId the id of the label that is put in front of the text field
     * @param model the model controlling the text field
     * @param nrColumns the width of the text field as the number of columns.
     *        The value 0 indicates that the width can be determined by the layout manager.
     */
    public void addField(String labelId, StringModel model, int nrColumns) {
        addField(labelId, beanFactory.createTextFieldBean(model, nrColumns));
    }

    /**
     * Adds a field to edit a double.
     * @param labelId the id of the label that is put in front of the text field
     * @param model the model controlling the text field
     */
    public void addField(String labelId, DoubleModel model) {
        addField(labelId, beanFactory.createDoubleFieldBean(model));
    }

    /**
     * Adds a field to edit a double.
     * @param labelId the id of the label that is put in front of the text field
     * @param model the model controlling the text field
     * @param nrColumns the width of the text field as the number of columns.
     *        The value 0 indicates that the width can be determined by the layout manager.
     */
    public void addField(String labelId, DoubleModel model, int nrColumns) {
        addField(labelId, beanFactory.createDoubleFieldBean(model, nrColumns));
    }

    /**
     * Adds a field to edit a password.
     * @param labelId the id of the label that is put in front of the text field
     * @param model the model controlling the text field
     * @param nrColumns the width of the text field as the number of columns.
     *        The value 0 indicates that the width can be determined by the layout manager.
     */
    public void addPasswordField(String labelId, StringModel model, int nrColumns) {
        addField(labelId, beanFactory.createPasswordBean(model, nrColumns));
    }

    /**
     * Adds a check box.
     * @param labelId the id of the label that is put in front of the check box
     * @param model the model controlling the check box
     */
    public void addField(String labelId, BooleanModel model) {
        addField(labelId, beanFactory.createCheckBoxBean(model));
    }

    /**
     * Adds a field to select a file.
     * @param labelId the id of the label that is put in front of the file selection bean.
     * @param model the model controlling the file seleciton bean
     */
    public void addField(String labelId, FileSelectionModel model) {
        addField(labelId, beanFactory.createFileSelectionBean(model));
    }


    /**
     * Adds a field to edit a string.
     * @param labelId the id of the label that is put in front of the text field
     * @param model the model controlling the text field
     */
    public void addField(String labelId, DateModel model) {
        addFieldWithConstraints(labelId, beanFactory.createDateSelectionBean(model),
        		SwingUtils.createLabelGBConstraints(1, nrFields));
    }

    /**
     * Adds a combo box to select a single item from a list of items.
     * @param labelId the id of the label that is put in front of the combo box
     * @param model the model controlling the combo box
     */
    public <T> void addComboBoxField(String labelId, ListModel<T> model, ObjectFormatter<T> itemFormatter) {
    	ComboBoxBean<T> bean = beanFactory.createComboBoxBean(model);
    	bean.setItemFormatter(itemFormatter);
        addFieldWithConstraints(labelId, bean,
        		SwingUtils.createLabelGBConstraints(1, nrFields));
    }

    /**
     * Adds a component. Use this method to add components for which the general
     * models (StringModel, DateModel etc.) cannot be used.
     * @param labelId the id of the label that is put in front of the component
     * @param component the component
     */
    public void addField(String labelId, JComponent component) {
    	addFieldWithConstraints(labelId, component, SwingUtils.createTextFieldGBConstraints(1, nrFields));
    }

    private void addFieldWithConstraints(String labelId, JComponent component, GridBagConstraints constraints) {
        JLabel label = WidgetFactory.getInstance().createLabel(labelId, component);
        add(label, SwingUtils.createLabelGBConstraints(0, nrFields));
        add(component, constraints);
        nrFields++;

        components.add(component);
    }

    @Override
    public void requestFocus() {
    	if (!components.isEmpty()) {
    		components.get(0).requestFocus();
    	}
    }

    @Override
    public boolean requestFocusInWindow() {
    	if (!components.isEmpty()) {
    		return components.get(0).requestFocusInWindow();
    	} else {
    		return false;
    	}
    }
}
