/*
 * $Id: SortedTable.java,v 1.5 2008-06-03 18:41:19 sanderk Exp $
 *
 * Copyright (C) 2005 Sander Kooijmans
 *
 */

package nl.gogognome.swing;

import java.awt.Component;
import java.awt.Container;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.ListSelectionModel;

/**
 * This interface specifies a Table component that allows the user to sort its rows by clicking
 * on the column's header.
 */
public interface SortedTable {

    /** Indicates descending order. */ 
    public static final int DESCENDING = -1;
    
    /** Indicates unsorted order. */
    public static final int NOT_SORTED = 0;
    
    /** Indicates ascending order. */
    public static final int ASCENDING = 1;

    /**
     * Gets the selection model. This selection model refers to the unsorted rows.
     * @return the selection model
     */
    public ListSelectionModel getSelectionModel();
    
    /**
     * Gets the actual {@link JComponent} that contains the table. This component should
     * be added to a {@link Container}.
     * 
     * @return the {@link JComponent}
     */
    public JComponent getComponent();
 
    /**
     * Sets the title for this table.
     * @param title the title
     */
    public void setTitle(String title);
    
    /**
     * Gets the component that can receive the focus.
     * @return the focusable component
     */
    public Component getFocusableComponent();
    
    /**
     * If the table is not empty, then this method selects the first row.
     */
    public void selectFirstRow();
    
    /**
     * Set the sorting states.
     * @param column the column to be sorted
     * @param status {@link #DESCENDING}, {@link #NOT_SORTED} or {@link #ASCENDING}
     */
    public void setSortingStatus(int column, int status);
    
    /**
     * Returns the indices of all selected rows.
     *
     * @return an array of integers containing the indices of all selected rows,
     *         or an empty array if no row is selected
     * @see #getSelectedRow
     */
    public int[] getSelectedRows();
    
    /**
     * Sets the action that is to be performed when the user selects a row in the table.
     * @param action the action
     */
    public void setSelectionAction(Action action);

}
