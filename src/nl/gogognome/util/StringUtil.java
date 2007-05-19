/*
 * $Id: StringUtil.java,v 1.1 2007-05-19 17:32:41 sanderk Exp $
 *
 * Copyright (C) 2007 Sander Kooijmans
 */
package nl.gogognome.util;

/**
 * This class contains utility methods for <code>String</code>s.
 *
 * @author Sander Kooijmans
 */
public class StringUtil {

    /**
     * Converts <code>null</code> to an empty string.
     * 
     * @param s a string or <code>null</code>
     * @return <code>s</code> if it is not <code>null</code>. Otherwise it returns an
     *          empty string
     */
    public static String nullToEmptyString(String s) {
        if (s == null) {
            return "";
        } else {
            return s;
        }
    }
}
