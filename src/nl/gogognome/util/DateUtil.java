/*
 * $Id: DateUtil.java,v 1.3 2007-09-15 18:58:44 sanderk Exp $
 *
 * Copyright (C) 2006 Sander Kooijmans
 */
package nl.gogognome.util;

import java.util.Calendar;
import java.util.Date;

/**
 * This class contains utility methods for <code>Date</code>s.
 *
 * @author Sander Kooijmans
 */
public class DateUtil {

    /** Calendar used to perform conversions. */
    private final static Calendar CALENDAR = Calendar.getInstance();
    
    /**
     * Compares two dates up to the day of year, ignoring their time within
     * the day.
     * 
     * @param date1 a date
     * @param date2 a date
     * @return a negative integer if <code>date1</code> comes before 
     *          <code>date2</code>;0 if <code>date1</code> represents the same day 
     *          as <code>date2</code>; a positive integer if <code>date1</code>
     *          comes after <code>date2</code>. 
     */
    public static int compareDayOfYear(Date date1, Date date2) {
        synchronized(CALENDAR) {
		    CALENDAR.setTime(date1);
		    int year1 = CALENDAR.get(Calendar.YEAR);
		    int dayOfYear1 = CALENDAR.get(Calendar.DAY_OF_YEAR);
		    
		    CALENDAR.setTime(date2);
		    int year2 = CALENDAR.get(Calendar.YEAR);
		    int dayOfYear2 = CALENDAR.get(Calendar.DAY_OF_YEAR);
		    
		    if (year1 < year2) {
		        return -1;
		    } else if (year1 > year2) {
		        return 1;
		    }
		    if (dayOfYear1 < dayOfYear2) {
		        return -1;
		    } else if (dayOfYear1 > dayOfYear2) {
		        return 1;
		    }
		    
		    return 0;
        }
    }
    
    /**
     * Gets a field of the date.
     * @param date the date
     * @param field the field (the same value as for <code>Calendar.get(int)</code>)
     * @return the value of the field
     */
    public static int getField(Date date, int field) {
        synchronized(CALENDAR) {
            CALENDAR.setTime(date);
            return CALENDAR.get(field);
        }
    }
    
    /**
     * Adds a number of days to a <code>Date</code>.
     * @param date the <code>Date</code>
     * @param numDays the number of days
     * @return the new date
     */
    public static Date addDays(Date date, int numDays) {
        synchronized(CALENDAR) {
            CALENDAR.setTime(date);
            CALENDAR.add(Calendar.DATE, numDays);
            return CALENDAR.getTime();
        }
    }
}
