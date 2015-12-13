package nl.gogognome.lib.collections;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;

public class DefaultValueMapTest {

    private final Map<String, Integer> stringToIntHashMap = new HashMap<>();
    private final Map<Integer, String> intToStringTreeMap = new TreeMap<>();

    private final DefaultValueMap<String, Integer> stringToIntDefaultValueMap = new DefaultValueMap<>(stringToIntHashMap, 1);
    private final DefaultValueMap<Integer, String> intToStringDefaultValueMap = new DefaultValueMap<>(intToStringTreeMap, "X");

    @Before
    public void initMaps() {
        stringToIntHashMap.put("abc", 123);
        stringToIntHashMap.put("def", 456);

        intToStringDefaultValueMap.put(123, "abc");
        intToStringDefaultValueMap.put(456, "def");
    }

    @Test
    public void whenValueIsPresentInMapGetShouldReturnValue() {
        assertEquals(Integer.valueOf(123), stringToIntDefaultValueMap.get("abc"));
        assertEquals(Integer.valueOf(456), stringToIntDefaultValueMap.get("def"));

        assertEquals("abc", intToStringTreeMap.get(123));
        assertEquals("def", intToStringTreeMap.get(456));
    }

    @Test
    public void whenValueIsAbsentInMapGetShouldReturnDefaultValue() {
        assertEquals(Integer.valueOf(1), stringToIntDefaultValueMap.get("bla"));
        assertEquals(Integer.valueOf(1), stringToIntDefaultValueMap.get("bloop"));
        assertEquals(Integer.valueOf(1), stringToIntDefaultValueMap.get(null));

        assertEquals("X", intToStringDefaultValueMap.get(0));
        assertEquals("X", intToStringDefaultValueMap.get(1000));
    }

    @Test(expected = NullPointerException.class)
    public void getKeyNullFromWrappedTreeMapThrowsNullPointerException() {
        assertEquals("X", intToStringDefaultValueMap.get((Integer) null));
    }
}