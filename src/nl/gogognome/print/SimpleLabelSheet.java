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
    @Override
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
    @Override
	public int getNrColumns() {
        return nrColumns;
    }

    /**
     * @see nl.gogognome.print.LabelSheet#getNrRows()
     */
    @Override
	public int getNrRows() {
        return nrRows;
    }

    /**
     * @see nl.gogognome.print.LabelSheet#isLabelAvailble(int, int)
     */
    @Override
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
