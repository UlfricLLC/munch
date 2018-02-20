package com.ulfric.munch.util;

import java.text.DecimalFormat;
import java.time.Month;
import java.time.Year;

public class TemporalUtil {

	public static String twoDigit(Month month) {
		int value = month.getValue();
		if (value >= 10) {
			return String.valueOf(value);
		}
		return "0" + value;
	}

	private static String twoDigit(Year year) {
		int value = year.getValue();
		if (value >= 2000) {
			value -= 2000;
		}
		return new DecimalFormat("00").format(value);
	}

	public static String mmyy(Month month, Year year) {
		return twoDigit(month) + twoDigit(year);
	}

}
