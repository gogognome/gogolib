/*
 * $Id: ComparatorUtil.java,v 1.1 2007-04-07 15:24:31 sanderk Exp $
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
}
