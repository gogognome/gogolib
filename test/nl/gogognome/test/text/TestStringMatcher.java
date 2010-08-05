/**
 *
 */
package nl.gogognome.test.text;

import junit.framework.TestCase;
import nl.gogognome.text.StringMatcher;

/**
 * Tests the class {@link StringMatcher}.
 */
public class TestStringMatcher extends TestCase {

	public void testCaseSensitiveMatch() {
		StringMatcher stringMatcher = new StringMatcher("abcabc");
		assertEquals(-1, stringMatcher.match(""));
		assertEquals(-1, stringMatcher.match("a"));
		assertEquals(-1, stringMatcher.match("abcab"));
		assertEquals(-1, stringMatcher.match("ABCABC"));
		assertEquals(0, stringMatcher.match("abcabc"));
		assertEquals(21, stringMatcher.match("abcdbcddfgasabcabsdasabcabc"));
	}

	public void testCaseInsensitiveMatch() {
		StringMatcher stringMatcher = new StringMatcher("abcabc", true);
		assertEquals(-1, stringMatcher.match(""));
		assertEquals(-1, stringMatcher.match("a"));
		assertEquals(-1, stringMatcher.match("abcab"));
		assertEquals(0, stringMatcher.match("ABCABC"));
		assertEquals(0, stringMatcher.match("abcabc"));
		assertEquals(21, stringMatcher.match("abcdbcddfgasabcabsdasabCAbc"));
	}

}
