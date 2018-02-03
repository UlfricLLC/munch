package com.ulfric.munch.util;

import java.util.Map;

public class CollectionUtil {

	public static boolean isNotEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}

	public static boolean isEmpty(Map<?, ?> map) {
		if (map == null) {
			return true;
		}

		return map.isEmpty();
	}

}
