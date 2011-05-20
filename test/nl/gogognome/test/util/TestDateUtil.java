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
package nl.gogognome.test.util;

import java.text.ParseException;
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

        cal.set(2008, 3-1, 31);
        date1 = cal.getTime();

        cal.set(2008, 4-1, 1);
        date2 = cal.getTime();
        assertEquals(-1, DateUtil.compareDayOfYear(date1, date2));
        assertEquals(1, DateUtil.compareDayOfYear(date2, date1));
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

    public void testFormatYYYYMMDD() {
    	assertEquals("20110520",
    			DateUtil.formatDateYYYYMMDD(DateUtil.createDate(2011, 5, 20, 16, 25, 24)));
    }

    public void testParseYYYYMMDD() throws ParseException {
    	assertEquals(DateUtil.createDate(2011, 5, 20),
    			DateUtil.parseDateYYYYMMDD("20110520"));

    	try {
    		DateUtil.parseDateYYYYMMDD("20110520a");
    		fail("Expected exception was not thrown!");
    	} catch (ParseException e) {
    		// expected exception
    	}

    	try {
    		DateUtil.parseDateYYYYMMDD(" 20110520");
    		fail("Expected exception was not thrown!");
    	} catch (ParseException e) {
    		// expected exception
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

        @Override
		public void run() {
            long endTime = System.currentTimeMillis() + 1000;
            while (succeeded && endTime > System.currentTimeMillis()) {
                succeeded = DateUtil.compareDayOfYear(date1, date2) < 0 &&
                	DateUtil.compareDayOfYear(date2, date1) > 0;
            }
        }
    }
}
