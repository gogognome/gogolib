/*
 * $Id: LabelSheet.java,v 1.1 2007-08-08 18:57:09 sanderk Exp $
 *
 * Copyright (C) 2005 Sander Kooijmans
 *
 */

package nl.gogognome.print;

/**
 * A <code>LabelSheetr</code> represents a sheet with labels. It holds
 * the number of rows and columns of labels and it can even indicate per label whether it is
 * available or not for printing.
 */

public interface LabelSheet {

    /**
     * Gets the number of rows of labels.
     * @return the number of rows
     */
    public int getNrRows();
    
    /**
     * Gets the number of columns of labels.
     * @return the number of columns
     */
    public int getNrColumns();
    
    /**
     * Checks whether the specified label is available. Only available labels can be printed.
     * @param row the row of the label
     * @param column the column of the label
     * @return <code>true</code> if the label is avaible; <code>false</code> otherwise
     */
    public boolean isLabelAvailble(int row, int column);
    
    /**
     * Returns the number of available labels on this sheet. 
     * @return the number of available labels on this sheet
     */
    public int getNrAvailableLabels();
}
