package nl.gogognome.lib.text;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;

/**
 * This class offers methods to format amounts of money in a number of ways.
 */
public class AmountFormat
{
    /** The locale used to format amounts. */
    private Locale locale;

    /** The currency of the amount. */
    private Currency currency;

    /**
     * Constructor.
     * @param locale the locale used to format amounts
     * @param currency the currency used to format amounts
     */
    public AmountFormat(Locale locale, Currency currency) {
        this.locale = locale;
        this.currency = currency;
    }

	private final static String EMPTY_STRING = "";

	private final static HashMap<String, Currency> SYMBOL_TO_CURRENCY_MAP = new HashMap<>();

	static {
	    SYMBOL_TO_CURRENCY_MAP.put("EUR", Currency.getInstance("EUR"));
	}

	/**
	 * Formats an amount of money.
	 * @param amount the amount to be formatted
	 * @return the formatted amount
	 */
	public String formatAmountWithoutCurrency(Number amount)
	{
	    return formatAmount(amount, EMPTY_STRING);
	}

	/**
	 * Formats an amount of money.
	 * @param amount the amount to be formatted specified in cents
	 * @return the formatted amount
	 */
	public String formatAmount(Number amount) {
		if (amount == null) {
			return "";
		}

        return formatAmount(amount, currency.getSymbol(locale));
	}

	/**
	 * Formats an amount of money.
	 * @param amount the amount to be formatted specified in cents
     * @param currencySymbol the currency symbol used as prefix of the formatted amount
	 * @return the formatted amount
	 */
	public String formatAmount(Number amount, String currencySymbol) {
		if (amount == null) {
			return "";
		}

        StringBuilder sb = new StringBuilder(amount.toString());
        int firstDigitIndex = 0;
        if (sb.charAt(0) == '-')
        {
            sb.insert(1, "/- ");
            firstDigitIndex += 4;
        }

        int numFractionDigits = currency.getDefaultFractionDigits();
        if (numFractionDigits > 0)
        {
            DecimalFormatSymbols dfs = new DecimalFormatSymbols(locale);
            int fractionDigitIndex = sb.length() - numFractionDigits;
            if (fractionDigitIndex <= firstDigitIndex)
            {
                // There are not enough digits present to insert the fraction separator.
                // Add extra zeros.
                while(fractionDigitIndex <= firstDigitIndex)
                {
                    sb.insert(firstDigitIndex, '0');
                    fractionDigitIndex++;
                }
            }
            sb.insert(fractionDigitIndex, dfs.getDecimalSeparator());
        }

        if (currencySymbol != null && currencySymbol.length() > 0)
        {
            sb.insert(firstDigitIndex, ' ');
            sb.insert(firstDigitIndex, currencySymbol);
        }

        return sb.toString();
	}

    /**
     * Parses a string containing an amount. The format of the amount is
     * <code>[MINUS]CUR d+[DS]d*</code> where <code>d</code> represents a digit,
     * <code>MINUS</code> stands for "-" or "-/- " (note the extra space at the
     * end), <code>CUR</code> is the currency symbol and
     * <code>DS</code> is the decimal separator.
     *
     * @param amountString the string containing an amount
     * @return the amount in cents
     * @throws ParseException if the string does not contain a valid amount
     */
    public BigInteger parse(String amountString, Currency expectedCurrency)
    	throws ParseException
    {
        StringBuilder sb = new StringBuilder(amountString);
        try
        {
	        int index = 0;
	        if (amountString.startsWith("-/- "))
	        {
	            sb.replace(0, 4, "-");
	            index++;
	        }

	        int currencyIndex = index;
	        StringBuilder currencySymbol = new StringBuilder(10);
	        while (!Character.isDigit(sb.charAt(index)) && !Character.isWhitespace(sb.charAt(index)))
	        {
	            currencySymbol.append(sb.charAt(index));
	            index++;
	        }

	        Currency currency = getCurrency(currencySymbol.toString());
            if (!currency.equals(expectedCurrency)) {
                throw new ParseException("Expected currency " + expectedCurrency.getDisplayName() + " but found " + currency.getDisplayName(), 0);
            }

	        sb.delete(currencyIndex, index + 1);
	        return convertToCents(sb.toString());
        } catch (ParseException e) {
            throw e;
        } catch (Exception e) {
            throw new ParseException(e.toString(), 0);
        }
    }

    /**
     * Parses a string containing an amount. The format of the amount is
     * <code>[MINUS]d+[DS]d*</code> where <code>d</code> represents a digit,
     * <code>MINUS</code> stands for "-" or "-/- " (note the extra space at the
     * end), and
     * <code>DS</code> is the decimal separator.
     *
     * @param amountString the string containing an amount
     * @return the amount in cents
     * @throws ParseException if the string does not contain a valid amount
     */
    public BigInteger parse(String amountString) throws ParseException {
        StringBuilder sb;
        try {
        	sb = new StringBuilder(amountString);
	        if (amountString.startsWith("-/- ")) {
	            sb.replace(0, 4, "-");
	        }

	        return convertToCents(sb.toString());
        } catch (Exception e) {
            throw new ParseException(e.toString(), 0);
        }
    }

    /**
     * Gets the <code>Currency</code> that corresponds to the specified symbol.
     * @param symbol the symbol
     * @return the currency (never null)
     * @throws ParseException if no currency was found
     */
    private Currency getCurrency(String symbol) throws ParseException {
        Currency currency = SYMBOL_TO_CURRENCY_MAP.get(symbol);
        if (currency == null) {
            for (Currency c : Currency.getAvailableCurrencies()) {
                if (symbol.equals(c.getDisplayName()) || symbol.equals(c.getCurrencyCode()) || symbol.equals(c.getSymbol())) {
                    SYMBOL_TO_CURRENCY_MAP.put(symbol, c);
                    currency = c;
                    break;
                }
            }
        }

        if (currency == null) {
            throw new ParseException("Unknown currency symbol found in \"" + symbol + "\"", 0);
        }

        return currency;
    }

    private BigInteger convertToCents(String amount) {
        int numFractionDigits = currency.getDefaultFractionDigits();
        if (numFractionDigits > 0)
        {
            DecimalFormatSymbols dfs = new DecimalFormatSymbols(locale);
            String fractionSeparator = Character.toString(dfs.getDecimalSeparator());
            StringBuilder sb = new StringBuilder(amount);
            int index = sb.indexOf(fractionSeparator);
            if (index == -1)
            {
                index = sb.length();
                sb.append(fractionSeparator);
            }

            // Append as many zeros as needed to let sb have exactly
            // numFractionDigits digits.
            while (numFractionDigits > (sb.length() - index - 1))
            {
                sb.append('0');
            }

            sb.deleteCharAt(index);
            amount = sb.toString();
        }
        return new BigInteger(amount);
    }
}
