/*
 * $Id: AbstractModel.java,v 1.2 2009-01-03 12:21:15 sanderk Exp $
 *
 * Copyright (C) 2006 Sander Kooijmans
 */
package nl.gogognome.framework.models;

import java.util.LinkedList;

/**
 * This class is the base class for all models. It maintains the list of listeners. 
 *
 * @author Sander Kooijmans
 */
public class AbstractModel {

    /** Contains the subscribed listeners. */
    private LinkedList<ModelChangeListener> listeners = new LinkedList<ModelChangeListener>();
    
    /**
     * Adds a model change listener to this model.
     * @param listener the listener
     */
    public void addModelChangeListener(ModelChangeListener listener) {
        listeners.add(listener);
    }
    
    /**
     * Removes a model change listener from this model.
     * @param listener the listener
     */
    public void removeModelChangeListener(ModelChangeListener listener) {
        listeners.remove(listener);
    }
    
    /** 
     * Notifies the subscribed listeners about a change in this model.
     * @param source if not <code>null</code>, then this indicates the
     *         listener that initiated this notification. If the listener
     *         is subcribed, it will not get notified by this method. 
     */
    protected void notifyListeners(ModelChangeListener source) {
        for (ModelChangeListener listener : listeners) {
            if (listener != source) {
                listener.modelChanged(this);
            }
        }
    }
}
