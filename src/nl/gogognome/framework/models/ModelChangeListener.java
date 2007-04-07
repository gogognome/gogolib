/*
 * $Id: ModelChangeListener.java,v 1.1 2007-04-07 15:23:47 sanderk Exp $
 *
 * Copyright (C) 2006 Sander Kooijmans
 */
package nl.gogognome.framework.models;

/**
 * This interface specifies a listener for changes in a model. 
 *
 * @author Sander Kooijmans
 */
public interface ModelChangeListener {

    /**
     * This method is called when the model has changed
     * @param model the model that has changed
     */
    public void modelChanged(AbstractModel model);
}
