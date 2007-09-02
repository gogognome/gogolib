/*
 * $Id: ViewListener.java,v 1.1 2007-09-02 19:49:55 sanderk Exp $
 *
 * Copyright (C) 2005 Sander Kooijmans
 *
 */

package nl.gogognome.framework;

/**
 * A listener for changes in the state of a view.
 */
public interface ViewListener {

    /** 
     * This method is called when the view is closed.
     * @param view the view thas is closed 
     */
    public void onViewClosed(View view);
}
