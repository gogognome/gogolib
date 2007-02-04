/*
 * $Id: TestDate.java,v 1.1 2007-02-04 16:40:16 sanderk Exp $
 *
 * Copyright (C) 2006 Sander Kooijmans
 */
package nl.gogognome.test.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

import junit.framework.TestCase;
import nl.gogognome.util.Date;

/**
 * This class tests the nl.gogognome.Date class. 
 *
 * @author Sander Kooijmans
 */
public class TestDate extends TestCase {

    public void testConstructors() {
        Date date1 = new Date(2007, 1, 4);
        
        Calendar cal = Calendar.getInstance();
        cal.set(2007, 1, 4);
        Date date2 = new Date(cal.getTime());
        
        assertEquals(date1, date2);
        
        Date today = new Date();
        
        assertEquals(new Date(new java.util.Date()), today);
        
        try {
            new Date(2006, 12, 1); // illegal month
            fail("Expected illegal argument exception");
        } catch (IllegalArgumentException e) {
            // ignore
        }
        
        try {
            new Date(2006, -1, 1); // illegal month
            fail("Expected illegal argument exception");
        } catch (IllegalArgumentException e) {
            // ignore
        }
        
        try {
            new Date(2006, 5, 32); // illegal day
            fail("Expected illegal argument exception");
        } catch (IllegalArgumentException e) {
            // ignore
        }
        
        try {
            new Date(2006, 5, 0); // illegal day
            fail("Expected illegal argument exception");
        } catch (IllegalArgumentException e) {
            // ignore
        }
    }
    
    public void testComparisons() {
        Date date1 = new Date(2005, 1, 4);
        Date date2 = new Date(2006, 1, 4);

        Date date3 = new Date(2007, 0, 4);
        Date date4 = new Date(2007, 1, 4);
        
        Date date5 = new Date(2007, 1, 3);
        Date date6 = new Date(2007, 1, 4);
        
        assertEquals(0, date1.compareTo(date1));
        assertTrue(date1.compareTo(date2) < 0);
        assertTrue(date2.compareTo(date1) > 0);

        assertTrue(date3.compareTo(date4) < 0);
        assertTrue(date4.compareTo(date3) > 0);

        assertTrue(date5.compareTo(date6) < 0);
        assertTrue(date6.compareTo(date5) > 0);
        
        assertFalse(date1.after(date2));
        assertTrue(date2.after(date1));
        
        assertFalse(date3.after(date4));
        assertTrue(date4.after(date3));

        assertFalse(date5.after(date6));
        assertTrue(date6.after(date5));

        assertTrue(date1.before(date2));
        assertFalse(date2.before(date1));
        
        assertTrue(date3.before(date4));
        assertFalse(date4.before(date3));
        
        assertTrue(date5.before(date6));
        assertFalse(date6.before(date5));
    }
    
    public void testThreadSafety() {
        TestThread[] threads = new TestThread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new TestThread(i);
            threads[i].start();
        }
        
        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join(); // wait for thread to finish
            } catch (InterruptedException e) {
                fail("Unexpected exception occurred: " + e);
            }
            assertTrue(threads[i].hasSucceeded());
        }
    }
    
    private static class TestThread extends Thread {
        
        private int year;
        private int month;
        private int day;
        private java.util.Date utilDate;

        private boolean succeeded = true;
        
        public TestThread(int nr) {
            year = 2000 + year;
            month = nr;
            day = nr + 1;
            Calendar cal = new GregorianCalendar();
            cal.set(year, month, day);
            utilDate = cal.getTime();
        }
        
        public boolean hasSucceeded() {
            return succeeded;
        }
        
        public void run() {
            long endTime = System.currentTimeMillis() + 1000;
            while (succeeded && endTime > System.currentTimeMillis()) {
                Date date = new Date(utilDate);
                if (date.getYear() != year || date.getMonth() != month 
                        || date.getDay() != day) {
                    succeeded = false;
                }
                if (!date.equals(new Date(date.getJavaUtilDate()))) {
                    succeeded = false;
                }
            }
        }
    }
}
