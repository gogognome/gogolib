/*
 * $Id: ComparatorUtil.java,v 1.2 2009-01-03 12:20:58 sanderk Exp $
 *
 * Copyright (C) 2006 Sander Kooijmans
 */
package nl.gogognome.util;

/**
 * This class offers convenience methods for comparing objects.
 *
 * @author Sander Kooijmans
 */
public class ComparatorUtil {

    /**
     * Checks whether <code>o1</code> and <code>o2</code> are equal.
     * If both are <code>null</code>, then they are also considered equal.
     * 
     * @param o1 an object (may be <code>null</code>)
     * @param o2 another object (possibly the same object) (may be <code>null</code>)
     * @return <code>true</code> if <code>o1</code> and <code>o2</code> are equal;
     *          <code>false</code> otherwise
     */
    public static boolean equals(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        }
        
        if (o1 == null && o2 == null) {
            return true;
        }
        
        if (o1 == null || o2 == null) {
            return false;
        }
        
        return o1.equals(o2);
    }

    /**
     * Compares two numbers and returns -1, 0 or 1 if <code>m</code> is less than,
     * equal to or larger than <code>n</code>.
     * @param m an int
     * @param n an int
     * @return -1, 0 or 1 if <code>m</code> is less than, equal to or larger 
     *         than <code>n</code>
     */
    public static int compareTo(int m, int n) {
        if (m < n) {
            return -1;
        } else if (m > n) {
            return 1;
        } else {
            return 0;
        }
    }
}
