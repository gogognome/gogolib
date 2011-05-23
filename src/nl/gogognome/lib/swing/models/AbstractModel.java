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
package nl.gogognome.lib.swing.models;

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
