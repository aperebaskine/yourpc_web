package com.pinguela.yourpc.web.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

public class URLBuilder {

	public static String getParameterizedUrl(HttpServletRequest request, String... ignoredParameters) {
		return appendParameters(request.getRequestURL(), request.getParameterMap(), ignoredParameters).toString();
	}

	public static <T> String appendParameters(String url, Map<String, T[]> parameters) {
		return appendParameters(new StringBuffer(url), parameters).toString();
	}

	public static String appendParameter(String url, String name, Object value) {
		return appendParameters(url, Map.of(name, new Object[]{value}));
	}

	private static <T> StringBuffer appendParameters(StringBuffer sb, Map<String, T[]> parameterMap, String... ignoredParameters) {

		List<String> ignoredParameterList = List.of(ignoredParameters);
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
			for (Object parameter : parameterMap.get(key)) {
				sb.append(key)
				.append('=')
				.append(URLEncoder.encode(parameter.toString(), StandardCharsets.UTF_8))
				.append("&");
			}
		}

		return sb.deleteCharAt(sb.length() -1);
	}

}