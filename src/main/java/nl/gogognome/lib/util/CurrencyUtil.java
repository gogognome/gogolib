/**
 *
 */
package nl.gogognome.lib.util;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;

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
		List<Locale> locales = List.of(Locale.getAvailableLocales());
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
