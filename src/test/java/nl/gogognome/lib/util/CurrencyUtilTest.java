package nl.gogognome.lib.util;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.*;

public class CurrencyUtilTest {

	@Test
	public void testGetCurrenciesForLocales_zeroLocales() {
		List<Currency> currencies = CurrencyUtil.getCurrenciesForLocales(List.of());
		assertEquals(List.of(), currencies);
	}

	@Test
	public void testGetCurrenciesForLocales_multipleLocalesWithDifferentCurrencies() {
		List<Currency> currencies = CurrencyUtil.getCurrenciesForLocales(List.of(Locale.US, Locale.FRANCE));
		List<String> currencyCodes = getCurrencyCodes(currencies);
		assertEquals(List.of("EUR", "USD"), currencyCodes);
	}

	@Test
	public void testGetCurrenciesForLocales_multipleLocalesWithSameCurrency() {
		List<Currency> currencies = CurrencyUtil.getCurrenciesForLocales(List.of(Locale.GERMANY, Locale.FRANCE, Locale.ITALY));
		List<String> currencyCodes = getCurrencyCodes(currencies);
		assertEquals(List.of("EUR"), currencyCodes);
	}

	@Test
	public void testGetCurrenciesForLocales_localeWithoutCurrency() {
		List<Currency> currencies = CurrencyUtil.getCurrenciesForLocales(List.of(Locale.of("es", "IC")));
		List<String> currencyCodes = getCurrencyCodes(currencies);
		assertEquals(List.of(), currencyCodes);
	}

	@Test
	public void testGetAllCurrencies() {
		List<Currency> currencies = CurrencyUtil.getAllCurrencies();
		List<String> currencyCodes = getCurrencyCodes(currencies);
		assertTrue(currencyCodes.contains("EUR"));
		assertTrue(currencyCodes.contains("USD"));
	}

	private List<String> getCurrencyCodes(List<Currency> currencies) {
		return currencies.stream()
				.map(c -> c.getCurrencyCode())
				.toList();
	}
}