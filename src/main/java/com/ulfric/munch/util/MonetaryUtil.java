package com.ulfric.munch.util;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

public class MonetaryUtil {

	public static String currencyCode(Currency currency) {
		return currency == null ? Currency.getInstance(Locale.US).getCurrencyCode()
				: currency.getCurrencyCode();
	}

	public static String toOneString(BigDecimal amount) {
		return amount.movePointRight(2).toPlainString();
	}

}
