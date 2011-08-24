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
package nl.gogognome.lib.test.text;

import static junit.framework.Assert.assertEquals;
import nl.gogognome.lib.text.StringMatcher;

import org.junit.Test;

/**
 * Tests the class {@link StringMatcher}.
 */
public class TestStringMatcher {

	@Test
	public void testCaseSensitiveMatch() {
		StringMatcher stringMatcher = new StringMatcher("abcabc");
		assertEquals(-1, stringMatcher.match(""));
		assertEquals(-1, stringMatcher.match("a"));
		assertEquals(-1, stringMatcher.match("abcab"));
		assertEquals(-1, stringMatcher.match("ABCABC"));
		assertEquals(0, stringMatcher.match("abcabc"));
		assertEquals(21, stringMatcher.match("abcdbcddfgasabcabsdasabcabc"));
	}

	@Test
	public void testCaseInsensitiveMatch() {
		StringMatcher stringMatcher = new StringMatcher("abcabc", true);
		assertEquals(-1, stringMatcher.match(""));
		assertEquals(-1, stringMatcher.match("a"));
		assertEquals(-1, stringMatcher.match("abcab"));
		assertEquals(0, stringMatcher.match("ABCABC"));
		assertEquals(0, stringMatcher.match("abcabc"));
		assertEquals(21, stringMatcher.match("abcdbcddfgasabcabsdasabCAbc"));
	}

	@Test
	public void testMatcherForEmptyString() {
		StringMatcher stringMatcher = new StringMatcher("", true);
		assertEquals(0, stringMatcher.match(""));
		assertEquals(0, stringMatcher.match("a"));
		assertEquals(0, stringMatcher.match("abcab"));

		stringMatcher = new StringMatcher("", false);
		assertEquals(0, stringMatcher.match(""));
		assertEquals(0, stringMatcher.match("a"));
		assertEquals(0, stringMatcher.match("abcab"));
	}

}
