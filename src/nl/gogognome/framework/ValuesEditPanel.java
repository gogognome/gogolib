/*
 * $Id: ValuesEditPanel.java,v 1.3 2009-02-24 21:31:57 sanderk Exp $
 *
 * Copyright (C) 2005 Sander Kooijmans
 *
 */

package nl.gogognome.framework;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import nl.gogognome.beans.DateSelectionBean;
import nl.gogognome.beans.TextFieldBean;
import nl.gogognome.framework.models.DateModel;
import nl.gogognome.framework.models.StringModel;
import nl.gogognome.swing.SwingUtils;
import nl.gogognome.swing.WidgetFactory;

/**
 * This class implemetns a panel containing a column of input fields.
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
