/*
 * $Id: TestDateUtil.java,v 1.1 2007-02-10 16:28:07 sanderk Exp $
 *
 * Copyright (C) 2006 Sander Kooijmans
 */
package nl.gogognome.test.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import junit.framework.TestCase;
import nl.gogognome.util.DateUtil;

/**
 * This class tests the <code>DateUtil</code> class. 
 *
 * @author Sander Kooijmans
 */
public class TestDateUtil extends TestCase {

    public void testComparasion() {
        Calendar cal = Calendar.getInstance();
        cal.set(2007, 1, 4);
        Date date1 = cal.getTime();
        
        cal.set(2007, 1, 5);
        Date date2 = cal.getTime();
        
        assertTrue(DateUtil.compareDayOfYear(date1, date2) < 0);
        assertTrue(DateUtil.compareDayOfYear(date2, date1) > 0);

        assertEquals(0, DateUtil.compareDayOfYear(date1, date1));
        assertEquals(0, DateUtil.compareDayOfYear(date2, date2));
        
        cal.set(2007, 1, 4, 10, 43, 10);
        date1 = cal.getTime();
        
        cal.set(2007, 1, 4, 11, 20, 15);
        date2 = cal.getTime();
        assertEquals(0, DateUtil.compareDayOfYear(date1, date2));
        assertEquals(0, DateUtil.compareDayOfYear(date2, date1));
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
        private Date date1;
        private Date date2;

        private boolean succeeded = true;
        
        public TestThread(int nr) {
            year = 2000 + year;
            month = nr;
            day = nr + 1;
            Calendar cal = new GregorianCalendar();
            cal.set(year, month, day);
            date1 = cal.getTime();
            
            cal.add(Calendar.DAY_OF_YEAR, 2);
            date2 = cal.getTime();
        }
        
        public boolean hasSucceeded() {
            return succeeded;
        }
        
        public void run() {
            long endTime = System.currentTimeMillis() + 1000;
            while (succeeded && endTime > System.currentTimeMillis()) {
                succeeded = DateUtil.compareDayOfYear(date1, date2) < 0 &&
                	DateUtil.compareDayOfYear(date2, date1) > 0;
            }
        }
    }
}
