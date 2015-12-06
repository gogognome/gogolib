package nl.gogognome.lib.test.text;

import nl.gogognome.lib.text.AmountFormat;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.Currency;
import java.util.Locale;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AmountFormatTest {

    private final AmountFormat dollarFormat = new AmountFormat(Locale.US, Currency.getInstance("USD"));
    private final AmountFormat euroFormat = new AmountFormat(new Locale("nl", "NL"), Currency.getInstance("EUR"));

    @Test
    public void testFormatting() {
        assertEquals("", dollarFormat.formatAmount(null));
        assertEquals("$ 0.00", dollarFormat.formatAmount(0));
        assertEquals("$ 1.00", dollarFormat.formatAmount(100));
        assertEquals("$ 1.20", dollarFormat.formatAmount(120));
        assertEquals("$ 1.23", dollarFormat.formatAmount(123));
        assertEquals("$ 1234.00", dollarFormat.formatAmount(123400));
        assertEquals("-/- $ 1.00", dollarFormat.formatAmount(-100));
        assertEquals("-/- $ 1.20", dollarFormat.formatAmount(-120));
        assertEquals("-/- $ 1.23", dollarFormat.formatAmount(-123));
        assertEquals("-/- $ 1234.00", dollarFormat.formatAmount(-123400));

        assertEquals("1234.00", dollarFormat.formatAmountWithoutCurrency(123400));
        assertEquals("-/- 1234.00", dollarFormat.formatAmountWithoutCurrency(-123400));

        assertEquals("$ 123456789012345678.90", dollarFormat.formatAmount(new BigInteger("12345678901234567890")));
        assertEquals("-/- $ 123456789012345678.90", dollarFormat.formatAmount(new BigInteger("-12345678901234567890")));

        assertEquals("BLA 0.00", dollarFormat.formatAmount(0, "BLA"));
        assertEquals("BLA 123.45", dollarFormat.formatAmount(12345, "BLA"));
        assertEquals("-/- BLA 123.45", dollarFormat.formatAmount(-12345, "BLA"));

        assertEquals("", euroFormat.formatAmount(null));
        assertEquals("€ 0,00", euroFormat.formatAmount(0));
        assertEquals("€ 1234,56", euroFormat.formatAmount(123456));
        assertEquals("-/- € 1234,56", euroFormat.formatAmount(-123456));

        assertEquals("€ 123456789012345678,90", euroFormat.formatAmount(new BigInteger("12345678901234567890")));
        assertEquals("-/- € 123456789012345678,90", euroFormat.formatAmount(new BigInteger("-12345678901234567890")));

        assertEquals("1234,00", euroFormat.formatAmountWithoutCurrency(123400));
        assertEquals("-/- 1234,00", euroFormat.formatAmountWithoutCurrency(-123400));
    }

    @Test
    public void testParsing() throws ParseException {
        assertEquals(new BigInteger("0"), dollarFormat.parse("0"));
        assertEquals(new BigInteger("100"), dollarFormat.parse("1"));
        assertEquals(new BigInteger("120"), dollarFormat.parse("1.2"));
        assertEquals(new BigInteger("1234"), dollarFormat.parse("12.34"));
        assertEquals(new BigInteger("-100"), dollarFormat.parse("-1"));
        assertEquals(new BigInteger("-100"), dollarFormat.parse("-/- 1"));
        assertEquals(new BigInteger("-120"), dollarFormat.parse("-1.2"));
        assertEquals(new BigInteger("-120"), dollarFormat.parse("-/- 1.2"));
        assertEquals(new BigInteger("-1234"), dollarFormat.parse("-12.34"));
        assertEquals(new BigInteger("-1234"), dollarFormat.parse("-/- 12.34"));

        assertEquals(new BigInteger("1234"), dollarFormat.parse("USD 12.34", Currency.getInstance("USD")));
        try {
            assertEquals(new BigInteger("1234"), dollarFormat.parse("USD 12.34", Currency.getInstance("EUR")));
            fail("Expected exception was not thrown");
        } catch (ParseException e) {
            assertEquals("Expected currency Euro but found US Dollar", e.getMessage());
        }

        assertEquals(new BigInteger("0"), euroFormat.parse("0"));
        assertEquals(new BigInteger("100"), euroFormat.parse("1"));
        assertEquals(new BigInteger("120"), euroFormat.parse("1,2"));
        assertEquals(new BigInteger("1234"), euroFormat.parse("12,34"));
        assertEquals(new BigInteger("-100"), euroFormat.parse("-1"));
        assertEquals(new BigInteger("-100"), euroFormat.parse("-/- 1"));
        assertEquals(new BigInteger("-120"), euroFormat.parse("-1,2"));
        assertEquals(new BigInteger("-120"), euroFormat.parse("-/- 1,2"));
        assertEquals(new BigInteger("-1234"), euroFormat.parse("-12,34"));
        assertEquals(new BigInteger("-1234"), euroFormat.parse("-/- 12,34"));

        try {
            assertEquals(new BigInteger("1234"), euroFormat.parse("EUR 12.34", Currency.getInstance("USD")));
            fail("Expected exception was not thrown");
        } catch (ParseException e) {
            assertEquals("Expected currency US Dollar but found Euro", e.getMessage());
        }
        assertEquals(new BigInteger("1234"), euroFormat.parse("EUR 12,34", Currency.getInstance("EUR")));
    }
}
