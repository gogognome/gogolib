/*
 * $Id: SortedTable.java,v 1.2 2008-03-10 21:16:51 sanderk Exp $
 *
 * Copyright (C) 2005 Sander Kooijmans
 *
 */

package nl.gogognome.swing;

import java.awt.Container;

import javax.swing.JComponent;
import javax.swing.ListSelectionModel;

/**
 * This interface specifies a Table component that allows the user to sort its rows by clicking
 * on the column's header.
 */
public interface SortedTable {

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
}
