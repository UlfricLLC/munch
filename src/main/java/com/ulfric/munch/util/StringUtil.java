package com.ulfric.munch.util;

public class StringUtil {

	public static boolean isNotBlank(String string) {
		return !isBlank(string);
	}

	public static boolean isBlank(String string) {
		if (string == null) {
			return true;
		}

		if (string.isEmpty()) {
			return true;
		}

		return string.trim().isEmpty();
	}

}
