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
package nl.gogognome.lib.util;

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
