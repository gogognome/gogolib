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
package nl.gogognome.lib.swing.views;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nl.gogognome.lib.gui.beans.DateSelectionBean;
import nl.gogognome.lib.gui.beans.TextFieldBean;
import nl.gogognome.lib.swing.SwingUtils;
import nl.gogognome.lib.swing.WidgetFactory;
import nl.gogognome.lib.swing.models.DateModel;
import nl.gogognome.lib.swing.models.StringModel;

/**
 * This class implements a panel containing a column of input fields.
 * Each input field consists of a label and a component (typically a text field).
 * The values of the fields are managed by models (e.g., {@link StringModel}
 * or {@link DateModel}.
 */
public class ValuesEditPanel extends JPanel {

    /** Contains the number of fields present in the panel. */
    private int nrFields;

    private List<Component> components = new ArrayList<Component>();

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
    public void deinitialize() {
        Iterator<Component> componentIter = components.iterator();
        while (componentIter.hasNext()) {
            Component component = componentIter.next();
            if (component instanceof TextFieldBean) {
                ((TextFieldBean) component).deinitialize();
            } else if (component instanceof DateSelectionBean) {
                ((DateSelectionBean) component).deinitialize();
            }
        }
    }

    /**
     * Adds a field to edit a string.
     * @param labelId the id of the label that is put in front of the text field
     * @param model the model controlling the text field
     */
    public void addField(String labelId, StringModel model) {
        addField(labelId, new TextFieldBean(model));
    }

    /**
     * Adds a field to edit a string.
     * @param labelId the id of the label that is put in front of the text field
     * @param model the model controlling the text field
     */
    public void addField(String labelId, DateModel model) {
        addField(labelId, new DateSelectionBean(model));
    }

    /**
     * Adds a component. Use this method to add components for which the general
     * models (StringModel, DateModel etc.) cannot be used.
     * @param labelId the id of the label that is put in front of the component
     * @param component the component
     */
    public void addField(String labelId, JComponent component) {
        JLabel label = WidgetFactory.getInstance().createLabel(labelId, component);
        add(label, SwingUtils.createLabelGBConstraints(0, nrFields));
        add(component, SwingUtils.createTextFieldGBConstraints(1, nrFields));
        nrFields++;

        components.add(component);
    }
}
