package nl.gogognome.lib.util;

import static org.junit.Assert.*;
import java.util.*;
import java.util.stream.*;
import org.junit.*;

public class CurrencyUtilTest {

	@Test
	public void testGetCurrenciesForLocales_zeroLocales() {
		List<Currency> currencies = CurrencyUtil.getCurrenciesForLocales(listOf());
		assertEquals(listOf(), currencies);
	}

	@Test
	public void testGetCurrenciesForLocales_multipleLocalesWithDifferentCurrencies() {
		List<Currency> currencies = CurrencyUtil.getCurrenciesForLocales(listOf(Locale.US, Locale.FRANCE));
		List<String> currencyCodes = getCurrencyCodes(currencies);
		assertEquals(listOf("EUR", "USD"), currencyCodes);
	}

	@Test
	public void testGetCurrenciesForLocales_multipleLocalesWithSameCurrency() {
		List<Currency> currencies = CurrencyUtil.getCurrenciesForLocales(listOf(Locale.GERMANY, Locale.FRANCE, Locale.ITALY));
		List<String> currencyCodes = getCurrencyCodes(currencies);
		assertEquals(listOf("EUR"), currencyCodes);
	}

	@Test
	public void testGetCurrenciesForLocales_localeWithoutCurrency() {
		List<Currency> currencies = CurrencyUtil.getCurrenciesForLocales(listOf(new Locale("es", "IC")));
		List<String> currencyCodes = getCurrencyCodes(currencies);
		assertEquals(listOf(), currencyCodes);
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
				.collect(Collectors.toList());
	}
	
	private static <T> List<T> listOf(T... values) {
		return Arrays.asList(values);
	} 
}