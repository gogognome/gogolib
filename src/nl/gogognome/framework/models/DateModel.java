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
package nl.gogognome.framework.models;

import java.util.Date;

import nl.gogognome.util.ComparatorUtil;

/**
 * This class implements a model for a <code>Date</code>.
 *
 * @author Sander Kooijmans
 */
public class DateModel extends AbstractModel {

    private Date date;

    public Date getDate() {
        return date;
    }

    /**
     * Sets the date of this model.
     * @param newDate the new value of the date
     * @param source the model change listener that sets the date. It will not
     *         get notified. It may be <code>null</code>.
     */
    public void setDate(Date newDate, ModelChangeListener source) {
        Date oldDate = this.date;
        if (!ComparatorUtil.equals(oldDate, newDate)) {
            this.date = newDate;
            notifyListeners(source);
        }
    }
}
