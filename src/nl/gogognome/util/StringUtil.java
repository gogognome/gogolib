/*
 * $Id: StringUtil.java,v 1.3 2009-01-03 12:20:45 sanderk Exp $
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
     * Checks wehther the string <code>s</code> is <code>null</code> or empty.
     * @param s a string
     * @return <code>true</code> if <code>s</code> is <code>null</code> or empty;
     *         <code>false</code> otherwise
     */
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.length() == 0;
    }

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
    
    public static String fillToSize(String s, int size, char c, boolean append) {
        int sizePadding = size - s.length();
        if (sizePadding == 0) {
            return s;
        }
        if (sizePadding < 0) {
            return s.substring(0, size);
        }
        StringBuilder sb = new StringBuilder(size);
        if (append) {
            sb.append(s);
        }
        for (int i=0; i<sizePadding; i++) {
            sb.append(c);
        }
        if (!append) {
            sb.append(s);
        }
        return sb.toString();
    }
}
