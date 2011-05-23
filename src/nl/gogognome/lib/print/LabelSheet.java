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
package nl.gogognome.lib.print;

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
