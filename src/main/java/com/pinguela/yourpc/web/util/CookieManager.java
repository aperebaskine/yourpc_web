package com.pinguela.yourpc.web.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieManager {
	
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				return cookie;
			}
		}
		return null;
	}
	
	public static String getValue(HttpServletRequest request, String name) {
		return getCookie(request, name).getValue();
	}
	
	public static void addCookie(HttpServletResponse response, String path, String name, String value, int ttl) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(ttl);
		cookie.setPath(path);
		response.addCookie(cookie);
	}
	
	public static void removeCookie(HttpServletResponse response, String path, String name) {
		addCookie(response, path, name, "", 0);
	}

}
