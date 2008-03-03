/*
 * $Id: SortedTableModel.java,v 1.1 2008-03-03 20:06:11 sanderk Exp $
 *
 * Copyright (C) 2005 Sander Kooijmans
 *
 */

package nl.gogognome.swing;

import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

/**
 * This interface specifies a table model that is to be used by {@link SortedTable}s. 
 */
public interface SortedTableModel extends TableModel {

    /**
     * Gets the width of the specified column.
     * @param column the column index
     * @return the width of the column in pixels
     */
    public int getColumnWidth(int column);
    
    /**
     * Gets the {@link TableCellRenderer} for the specified column.
     * If no specific renderer is needed, then <code>null</code> shoudl be returned.
     * @param column the column index
     * @return the renderer or <code>null</code>
     */
    public TableCellRenderer getRendererForColumn(int column);
}
