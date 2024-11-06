package com.pinguela.yourpc.web.util;

import java.util.Locale;

public class LocaleUtils {
		
	public static final Locale toLocale(String localeStr) {
		return Locale.forLanguageTag(localeStr);
	}
	
}
