/*
 * $Id: Date.java,v 1.1 2007-02-04 16:40:16 sanderk Exp $
 *
 * Copyright (C) 2006 Sander Kooijmans
 */
package nl.gogognome.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * This class implements a date. The difference with a <code>java.util.Date</code>
 * is that this class does not store a time within a day; it only stores a date.
 *
 * <p>This implementation is thread safe.
 * 
 * @author Sander Kooijmans
 */
public class Date implements Comparable {

    private int year;
    
    private int month;
    
    private int day;

    /** Calendar used to convert from and to <code>java.util.Date</code>s. */
    private final static Calendar CALENDAR = new GregorianCalendar();
    
    /**
     * Constructs a date for today.
     */
    public Date() {
        this(new java.util.Date());
    }
    
    /**
     * Constructor.
     * @param year the year
     * @param month the month (0 to 11)
     * @param day the day (1 to 31)
     */
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        if (month < 0 || month > 11) {
            throw new IllegalArgumentException("Invalid month: " + month);
        }
        if (day < 1 || day > 31) {
            throw new IllegalArgumentException("Invalid day of month: " + day);
        }
    }
    
    /**
     * Constructs a date from a <code>java.util.Date</code>
     * @param date the <code>java.util.Date</code>
     */
    public Date(java.util.Date date) {
        synchronized(CALENDAR) {
	        CALENDAR.setTime(date);
	        this.year = CALENDAR.get(Calendar.YEAR);
	        this.month = CALENDAR.get(Calendar.MONTH);
	        this.day = CALENDAR.get(Calendar.DAY_OF_MONTH);
        }
    }

    /**
     * Converts this date to a <code>java.util.Date</code>
     * @return the <code>java.util.Date</code>
     */
    public java.util.Date getJavaUtilDate() {
        synchronized(CALENDAR) {
            CALENDAR.set(year, month, day);
            return CALENDAR.getTime();
        }
    }
    
    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object o) {
        Date that = (Date)o;
        if (this.year > that.year) {
            return 1;
        } else if (this.year < that.year) {
            return -1;
        }
        
        // years are equal
        if (this.month > that.month) {
            return 1;
        } else if (this.month < that.month) {
            return -1;
        }
        
        // years and months are equal
        if (this.day > that.day) {
            return 1;
        } else if (this.day < that.day) {
            return -1;
        }
        
        // years, months and days are equal
        return 0;
    }
    
    /**
     * Checks whether the specified date is before this date.
     * @param date the date
     * @return <code>true</code> if the specified date is before this date;
     *          <code>false</code> otherwise
     */
    public boolean before(Date date) {
        return compareTo(date) < 0;
    }
    
    /**
     * Checks whether the specified date is after this date.
     * @param date the date
     * @return <code>true</code> if the specified date is after this date;
     *          <code>false</code> otherwise
     */
    public boolean after(Date date) {
        return compareTo(date) > 0;
    }
    
    /**
     * Checks whether an object is equal to this date.
     * @param o the object
     * @return <code>true</code> if <code>o</code> is equal to this date;
     *          <code>false</code> otherwise
     */
    public boolean equals(Object o) {
        if (o instanceof Date) {
            Date that = (Date)o;
            return this.year == that.year && this.month == that.month &&
            	this.day == that.day;
        }
        return false;
    }
    
    /**
     * Gets a string representation of this date.
     * @return a string representation of this date
     */
    public String toString() {
        return Integer.toString(year) + '-' + Integer.toString(month + 1) + '-' +
        	Integer.toString(day);
    }
    
    public int getDay() {
        return day;
    }
    public int getMonth() {
        return month;
    }
    public int getYear() {
        return year;
    }
}
