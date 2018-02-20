package com.ulfric.munch.util;

public class AddressUtil {

	public static String singleLineAddress(String line1, String line2) {
		StringBuilder builder = new StringBuilder();
		builder.append(line1);
		if (StringUtil.isNotBlank(line2)) {
			builder.append(' ');
			builder.append(line2);
		}
		return builder.toString().trim();
	}

}
