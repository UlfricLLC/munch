package com.ulfric.munch.util;

import java.time.Month;

public class TemporalUtil {

	public static String twoDigit(Month month) {
		int value = month.getValue();
		if (value >= 10) {
			return String.valueOf(value);
		}
		return "0" + value;
	}

}
