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
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import nl.gogognome.lib.swing.SwingUtils;
import nl.gogognome.lib.swing.models.AbstractModel;
import nl.gogognome.lib.swing.models.BooleanModel;
import nl.gogognome.lib.swing.models.ModelChangeListener;

/**
 * This class implements a bean for selecting a <code>Boolean</code>.
 *
 * @author Sander Kooijmans
 */
public class CheckBoxBean extends JPanel implements Bean {

    /** The model that stores the boolean of this bean. */
    private BooleanModel booleanModel;

    /** The check box. */
    private JCheckBox checkBox;

    /** The model change listener for the boolean model. */
    private ModelChangeListener modelChangeListener;

    /** The item listener for the check box. */
    private ItemListener itemListener;

    /**
     * Constructor.
     * @param booleanModel the model that will reflect the content of the bean
     */
    public CheckBoxBean(BooleanModel booleanModel) {
        this.booleanModel = booleanModel;
    }

    @Override
	public void initBean() {
        setOpaque(false);

        setLayout(new GridBagLayout());

        checkBox = new JCheckBox();

        updateCheckBox();
        modelChangeListener = new ModelChangeListener() {

            @Override
			public void modelChanged(AbstractModel model) {
                updateCheckBox();
            }

        };
        booleanModel.addModelChangeListener(modelChangeListener);

        itemListener = new ItemListenerImpl();
        checkBox.addItemListener(itemListener);
        add(checkBox, SwingUtils.createGBConstraints(0,0, 1, 1, 1.0, 0.0,
            GridBagConstraints.WEST, GridBagConstraints.NONE,
            0, 0, 0, 0));
    }

	/**
     * Deinitializes this bean. It will free its resources.
     */
    @Override
	public void close() {
        booleanModel.removeModelChangeListener(modelChangeListener);
        checkBox.removeItemListener(itemListener);
        modelChangeListener = null;
        itemListener = null;
        booleanModel = null;
        checkBox = null;
    }

    @Override
	public void addFocusListener(FocusListener listener) {
    	checkBox.addFocusListener(listener);
    }

    @Override
	public void removeFocusListener(FocusListener listener) {
        checkBox.removeFocusListener(listener);
    }

    /**
     * Updates the check box with the value of the boolean model.
     */
    private void updateCheckBox() {
        checkBox.setSelected(booleanModel.getBoolean());
    }

    private class ItemListenerImpl implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			booleanModel.setBoolean(checkBox.isSelected(), modelChangeListener);
		}

    }
}
