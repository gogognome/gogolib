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
