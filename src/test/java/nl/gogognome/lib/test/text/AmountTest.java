package nl.gogognome.lib.test.text;

import nl.gogognome.lib.text.Amount;
import org.junit.Test;

import java.util.Currency;
import java.util.Locale;

import static org.junit.Assert.*;

public class AmountTest {

    @Test
    public void testStaticAdd() {
        Amount one = new Amount("1", Currency.getInstance("EUR"), Locale.US);
        Amount two = new Amount("2", Currency.getInstance("EUR"), Locale.US);

        assertEquals(two, Amount.add(one, one));
        assertEquals(one, Amount.add(one, null));
        assertEquals(one, Amount.add(null, one));
        assertEquals(null, Amount.add(null, null));
    }

    @Test
    public void testAreEqual() {
        Amount one = new Amount("1", Currency.getInstance("EUR"), Locale.US);
        Amount two = new Amount("2", Currency.getInstance("EUR"), Locale.US);

        assertTrue(Amount.areEqual(one, one));
        assertTrue(Amount.areEqual(null, null));
        assertFalse(Amount.areEqual(one, two));
        assertFalse(Amount.areEqual(one, null));
        assertFalse(Amount.areEqual(null, one));
    }
}
