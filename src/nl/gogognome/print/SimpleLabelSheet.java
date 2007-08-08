/*
 * $Id: SimpleLabelSheet.java,v 1.1 2007-08-08 18:57:20 sanderk Exp $
 *
 * Copyright (C) 2005 Sander Kooijmans
 *
 */

package nl.gogognome.print;

/**
 * This class implements a sheet of which the number of rows and columns are configurable.  
 */
public class SimpleLabelSheet implements LabelSheet {

    /** 
     * <code>unavailableLabels[row][column] == true</code> if and only if
     * the label <code>(row, column)</code> is <b>un</b>available.
     * It was chosen to use unavailable instead of available, because by 
     * default a boolean array is initialize with all values to <code>false</code>. 
     */
    private boolean[][] unavailableLabels;
    
    private int nrRows;
    
    private int nrColumns;
    
    public SimpleLabelSheet(int nrRows, int nrColumns) {
        this.nrRows = nrRows;
        this.nrColumns = nrColumns;
        unavailableLabels = new boolean[nrRows][nrColumns];
    }
    
    /**
     * @see nl.gogognome.print.LabelSheet#getNrAvailableLabels()
     */
    public int getNrAvailableLabels() {
        int sum = 0;
        for (int r = 0; r<getNrRows(); r++) {
            for (int c=0; c<getNrColumns(); c++) {
                if (isLabelAvailble(r, c)) {
                    sum++;
                }
            }
        }
        return sum;
    }

    /**
     * @see nl.gogognome.print.LabelSheet#getNrColumns()
     */
    public int getNrColumns() {
        return nrColumns;
    }

    /**
     * @see nl.gogognome.print.LabelSheet#getNrRows()
     */
    public int getNrRows() {
        return nrRows;
    }

    /**
     * @see nl.gogognome.print.LabelSheet#isLabelAvailble(int, int)
     */
    public boolean isLabelAvailble(int row, int column) {
        return !unavailableLabels[row][column];
    }

    /**
     * Set the availablity of the specified label.
     * @param row the row
     * @param column the column
     * @param available <code>true</code> if the label is available; <code>false</code> otherwise
     */
    public void setAvailable(int row, int column, boolean available) {
        unavailableLabels[row][column] = !available;
    }
}
