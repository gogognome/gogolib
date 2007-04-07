/*
 * $Id: DateModel.java,v 1.1 2007-04-07 15:23:47 sanderk Exp $
 *
 * Copyright (C) 2006 Sander Kooijmans
 */
package nl.gogognome.framework.models;

import java.util.Date;

import nl.gogognome.util.ComparatorUtil;

/**
 * This class implements a model for a <code>Date</code>.
 *
 * @author Sander Kooijmans
 */
public class DateModel extends AbstractModel {

    private Date date;
    
    public Date getDate() {
        return date;
    }
    
    /**
     * Sets the date of this model.
     * @param newDate the new value of the date
     * @param source the model change listener that sets the date. It will not
     *         get notified. It may be <code>null</code>.
     */
    public void setDate(Date newDate, ModelChangeListener source) {
        Date oldDate = this.date;
        if (!ComparatorUtil.equals(oldDate, newDate)) {
            this.date = newDate;
            notifyListeners(source);
        }
    }
}
