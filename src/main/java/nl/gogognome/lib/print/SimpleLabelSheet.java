/*
   Copyright 2011 Sander Kooijmans

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package nl.gogognome.lib.print;

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
     * @see nl.gogognome.lib.print.LabelSheet#getNrAvailableLabels()
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
     * @see nl.gogognome.lib.print.LabelSheet#getNrColumns()
     */
    @Override
	public int getNrColumns() {
        return nrColumns;
    }

    /**
     * @see nl.gogognome.lib.print.LabelSheet#getNrRows()
     */
    @Override
	public int getNrRows() {
        return nrRows;
    }

    /**
     * @see nl.gogognome.lib.print.LabelSheet#isLabelAvailble(int, int)
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
