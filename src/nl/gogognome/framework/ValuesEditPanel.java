/*
 * $Id: ValuesEditPanel.java,v 1.2 2009-02-19 21:17:13 sanderk Exp $
 *
 * Copyright (C) 2005 Sander Kooijmans
 *
 */

package nl.gogognome.framework;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.util.Iterator;
import java.util.List;
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

    private List<Component> components;

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
            } else {
                throw new IllegalStateException("Unknown component found: " + component);
            }
        }
    }

    /**
     * Adds a field to edit a string.
     * @param labelId the id of the label that is put in front of the text field
     * @param model the model controlling the text field
     */
    public void addField(String labelId, StringModel model) {
        TextFieldBean textfield = new TextFieldBean(model);
        JLabel label = WidgetFactory.getInstance().createLabel(labelId, textfield);
        add(label, SwingUtils.createLabelGBConstraints(0, nrFields));
        add(textfield, SwingUtils.createTextFieldGBConstraints(1, nrFields));
        nrFields++;

        components.add(textfield);
    }

    /**
     * Adds a field to edit a string.
     * @param labelId the id of the label that is put in front of the text field
     * @param model the model controlling the text field
     */
    public void addField(String labelId, DateModel model) {
        DateSelectionBean dateSelectionBean = new DateSelectionBean(model);
        JLabel label = WidgetFactory.getInstance().createLabel(labelId, dateSelectionBean);
        add(label, SwingUtils.createLabelGBConstraints(0, nrFields));
        add(dateSelectionBean, SwingUtils.createTextFieldGBConstraints(1, nrFields));
        nrFields++;

        components.add(dateSelectionBean);
    }

}
