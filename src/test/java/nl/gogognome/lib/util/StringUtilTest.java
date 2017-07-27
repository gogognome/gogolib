package nl.gogognome.lib.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringUtilTest {

    @Test
    public void testIsNullOrEmpty() {
        assertTrue(StringUtil.isNullOrEmpty(null));
        assertTrue(StringUtil.isNullOrEmpty(""));
        assertFalse(StringUtil.isNullOrEmpty("x"));
        assertFalse(StringUtil.isNullOrEmpty("bla"));
    }

    @Test
    public void nullToEmptyString() {
        assertEquals("", StringUtil.nullToEmptyString(null));
        assertEquals("", StringUtil.nullToEmptyString(""));
        assertEquals("x", StringUtil.nullToEmptyString("x"));
        assertEquals("bla", StringUtil.nullToEmptyString("bla"));
    }

    @Test
    public void testReplace() {
        assertEquals("", StringUtil.replace("", 0, 0, ""));
        assertEquals("bla", StringUtil.replace("", 0, 0, "bla"));
        assertEquals("bla234", StringUtil.replace("01234", 0, 2, "bla"));
        assertEquals("012bla", StringUtil.replace("01234", 3, 5, "bla"));
        assertEquals("01bla4", StringUtil.replace("01234", 2, 4, "bla"));
    }

    @Test
    public void testPrependToSize() {
        assertEquals("", StringUtil.prependToSize("", 0, 'x'));
        assertEquals("xxxxx", StringUtil.prependToSize("", 5, 'x'));
        assertEquals("01234", StringUtil.prependToSize("0123456789", 5, 'x'));
        assertEquals("xxx12", StringUtil.prependToSize("12", 5, 'x'));
    }

    @Test
    public void testAppendToSize() {
        assertEquals("", StringUtil.appendToSize("", 0, 'x'));
        assertEquals("xxxxx", StringUtil.appendToSize("", 5, 'x'));
        assertEquals("01234", StringUtil.appendToSize("0123456789", 5, 'x'));
        assertEquals("12xxx", StringUtil.appendToSize("12", 5, 'x'));
    }

}