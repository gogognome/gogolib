/**
 *
 */
package nl.gogognome.lib.util;

import java.util.Comparator;
import java.util.Date;

/**
 * Comparator that compares dates up to the day of the year, ignoring
 * the time within the day.
 *
 * @author Sander Kooijmans
 */
public class DayOfYearComparator implements Comparator<Object> {
    @Override
	public int compare(Object o1, Object o2) {
        Date d1 = (Date) o1;
        Date d2 = (Date) o2;
        return DateUtil.compareDayOfYear(d1, d2);
    }
}
