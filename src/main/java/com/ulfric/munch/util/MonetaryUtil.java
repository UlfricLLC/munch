package com.ulfric.munch.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Locale;

import com.ulfric.munch.model.MonetaryAmount;

public class MonetaryUtil {

	public static String currencyCode(MonetaryAmount amount) {
		return amount.getCurrency() == null ? Currency.getInstance(Locale.US).getCurrencyCode()
				: amount.getCurrency().getCurrencyCode();
	}

	public static String toOneString(MonetaryAmount amount) {
		return toBigDecimal(amount).movePointRight(2).toPlainString();
	}

	public static BigDecimal toBigDecimal(MonetaryAmount amount) {
		StringBuilder value = new StringBuilder();
		if (amount.getDollars() != null) {
			value.append(amount.getDollars());
		} else {
			value.append(0);
		}

		if (amount.getCents() != null) {
			value.append('.');
			value.append(amount.getCents());
		}

		return new BigDecimal(value.toString()).setScale(2, RoundingMode.DOWN);
	}

}
