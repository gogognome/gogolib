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

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JComboBox;

import nl.gogognome.lib.swing.models.AbstractModel;
import nl.gogognome.lib.swing.models.ListModel;
import nl.gogognome.lib.swing.models.ModelChangeListener;
import nl.gogognome.lib.text.TextResource;

/**
 * This class implements a bean for selecting an item in a list of items.
 *
 * @author Sander Kooijmans
 */
public class ComboBoxBean<T> extends JComboBox implements Bean {

	private List<T> items;

	private ListModel<T> listModel;

	private ModelChangeListener modelChangeListener;

	private ItemListener itemListener;

	private String resourcePrefix;

	/**
	 * Constructor.
	 * @param listModel the list model containing the items and managing the selected item
	 * @param resourcePrefix if not null, then the each item is represented by a string resource.
	 *        The id of the item's string resource consists of this prefix followed by the
	 *        result of toString() of the item.
	 */
	public ComboBoxBean(ListModel<T> listModel, String resourcePrefix) {
		super();
		this.listModel = listModel;
		this.resourcePrefix = resourcePrefix;
	}

	@Override
	public void initBean() {
		updateItems();

		setSelectedIndex(listModel.getSingleSelectedIndex());

		modelChangeListener = new ModelChangeListener() {
			@Override
			public void modelChanged(AbstractModel model) {
				onModelChanged();
			}
		};

		listModel.addModelChangeListener(modelChangeListener);

		itemListener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				onItemStateChanged();
			}
		};
		addItemListener(itemListener);
	}

	private void updateItems() {
		items = listModel.getItems();
		for (T item : listModel.getItems()) {
			addItem(getItemToBeDisplayed(item));
		}
	}

	private Object getItemToBeDisplayed(Object item) {
		TextResource tr = TextResource.getInstance();
		if (resourcePrefix != null) {
			String id = resourcePrefix + item.toString();
			if (tr.containsString(id)) {
				item = tr.getString(id);
			}
		}
		return item;
	}

	private void onModelChanged() {
		if (listModel.getItems() != items) {
			updateItems();
		}

		if (getSelectedIndex() != listModel.getSingleSelectedIndex()) {
			setSelectedIndex(listModel.getSingleSelectedIndex());
		}

		setEnabled(listModel.isEnabled());
	}

	private void onItemStateChanged() {
		if (getSelectedIndex() != listModel.getSingleSelectedIndex()) {
			listModel.setSelectedIndices(new int[] { getSelectedIndex() }, modelChangeListener);
		}
	}

	@Override
	public void deinitialize() {
		listModel.removeModelChangeListener(modelChangeListener);
		removeItemListener(itemListener);
	}
}
