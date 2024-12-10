package com.pinguela.yourpc.web.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

public class URLBuilder {
	
	private static final String[] EMPTY_ARRAY = {};

	public static String getParameterizedUrl(HttpServletRequest request, String... ignoredParameters) {
		return appendParameters(request.getRequestURL(), request.getParameterMap(), ignoredParameters).toString();
	}

	public static String appendParameters(String url, Map<String, String[]> parameters) {
		return appendParameters(new StringBuffer(url), parameters).toString();
	}

	private static String appendParameters(StringBuffer sb, Map<String, String[]> parameters) {
		return appendParameters(sb, parameters, EMPTY_ARRAY).toString();
	}

	public static String appendParameter(String url, String name, String value) {
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put(name, new String[] {value});
		return appendParameters(new StringBuffer(url), map);
	}
	
	private static StringBuffer appendParameters(StringBuffer sb, 
			Map<String, String[]> parameterMap, String... ignoredParameters) {

		List<String> ignoredParameterList = Arrays.asList(ignoredParameters);
		String[] filteredKeys = parameterMap.keySet().stream()
				.filter(p -> !ignoredParameterList.contains(p)).toArray(String[]::new);

		if (filteredKeys.length < 1) {
			return sb;
		}

		int paramIndex = sb.indexOf("?");
		if (paramIndex < 0) {
			sb.append("?");
		} else if (paramIndex < sb.length() -1) {
			sb.append("&");
		}

		for (String key : filteredKeys) {
			for (String parameter : parameterMap.get(key)) {
				try {
					sb.append(key)
					.append('=')
					.append(URLEncoder.encode(parameter, "UTF-8"))
					.append("&");
				} catch (UnsupportedEncodingException e) {
					// TODO: Fix
				}
			}
		}

		return sb.deleteCharAt(sb.length() -1);
	}

}
