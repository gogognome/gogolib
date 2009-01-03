/*
 * $Id: StringModel.java,v 1.1 2009-01-03 12:21:15 sanderk Exp $
 *
 * Copyright (C) 2006 Sander Kooijmans
 */
package nl.gogognome.framework.models;

import nl.gogognome.util.ComparatorUtil;

/**
 * This class implements a model for a <code>String</code>.
 *
 * @author Sander Kooijmans
 */
public class StringModel extends AbstractModel {

    private String string;
    
    public String getString() {
        return string;
    }
    
    /**
     * Sets the string of this model.
     * @param newString the new value of the string
     * @param source the model change listener that sets the date. It will not
     *         get notified. It may be <code>null</code>.
     */
    public void setString(String newString, ModelChangeListener source) {
        String oldString = this.string;
        if (!ComparatorUtil.equals(oldString, newString)) {
            this.string = newString;
            notifyListeners(source);
        }
    }
}
