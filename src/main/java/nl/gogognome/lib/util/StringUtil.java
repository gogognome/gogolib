/*
   Copyright 2011 Sander Kooijmans

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package nl.gogognome.lib.util;

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

    /**
     * Replaces a substring in <code>s</code> by <code>replacement</code>
     * @param s a string
     * @param startIndex the start index of the substring that is to be replaced
     * @param endIndex the end index of the substring that is to be replaced
     * @param replacement the replacement
     * @return the string after replacement
     */
    public static String replace(String s, int startIndex, int endIndex, String replacement) {
        return s.substring(0, startIndex) + replacement + s.substring(endIndex);
    }

    /**
     * Fills a string to the specified size by prepending the string with the specified character.
     * @param s the string
     * @param size the size of the returned string
     * @param c the character used to prepend the string if needed
     * @return a string of exactly the specified size
     */
    public static String prependToSize(String s, int size, char c) {
        int sizePadding = size - s.length();
        if (sizePadding == 0) {
            return s;
        }
        if (sizePadding < 0) {
            return s.substring(0, size);
        }
        StringBuilder sb = new StringBuilder(size);
        for (int i=0; i<sizePadding; i++) {
            sb.append(c);
        }
        sb.append(s);
        return sb.toString();
    }

    /**
     * Fills a string to the specified size by appending the string with the specified character.
     * @param s the string
     * @param size the size of the returned string
     * @param c the character used to append the string if needed
     * @return a string of exactly the specified size
     */
    public static String appendToSize(String s, int size, char c) {
        int sizePadding = size - s.length();
        if (sizePadding == 0) {
            return s;
        }
        if (sizePadding < 0) {
            return s.substring(0, size);
        }
        StringBuilder sb = new StringBuilder(size);
        sb.append(s);
        for (int i=0; i<sizePadding; i++) {
            sb.append(c);
        }
        return sb.toString();
    }
}
