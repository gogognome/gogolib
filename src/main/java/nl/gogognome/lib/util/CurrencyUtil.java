/**
 *
 */
package nl.gogognome.lib.util;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for currencies.
 *
 * @author Sander Kooijmans
 */
public class CurrencyUtil {

	private final static Logger LOGGER = LoggerFactory.getLogger(CurrencyUtil.class);

	private CurrencyUtil() {
	}

	public static List<Currency> getAllCurrencies() {
		List<Locale> locales = Arrays.asList(Locale.getAvailableLocales());
		return getCurrenciesForLocales(locales);
	}

	public static List<Currency> getCurrenciesForLocales(List<Locale> locales) {
		TreeSet<String> currencyCodes = new TreeSet<String>();
		for (Locale locale : locales) {
			try {
				if (locale.getCountry() != null && locale.getCountry().length() == 2) {
					Currency currency = Currency.getInstance(locale);
					currencyCodes.add(currency.getCurrencyCode());
				}
			} catch (IllegalArgumentException e) {
				LOGGER.info("No currency found for locale " + locale);
			} catch (Exception e) {
				LOGGER.warn("Could not get currency for locale " + locale, e);
			}
		}

		List<Currency> currencies = new ArrayList<Currency>(currencyCodes.size());
		for (String currencyCode : currencyCodes) {
			currencies.add(Currency.getInstance(currencyCode));
		}
		return currencies;
	}
}
