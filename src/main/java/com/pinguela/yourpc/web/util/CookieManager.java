package com.pinguela.yourpc.web.util;

import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieManager {

	private static final String ROOT_PATH = "/";
	public static final int DEFAULT_TTL = 604800;

	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		
		for (Cookie cookie : Optional.ofNullable(cookies).orElse(new Cookie[0])) {
			if (cookie.getName().equals(name)) {
				return cookie;
			}
		}
		return null;
	}

	public static String getValue(HttpServletRequest request, String name) {
		Cookie cookie = getCookie(request, name);
		return cookie == null ? null : cookie.getValue();
	}

	public static void addCookie(HttpServletResponse response, String name, String value) {
		addCookie(response, ROOT_PATH, name, value, DEFAULT_TTL);
	}

	public static void addCookie(HttpServletResponse response, String path, String name, String value) {
		addCookie(response, path, name, value, DEFAULT_TTL);
	}

	public static void addCookie(HttpServletResponse response, String name, String value, int ttl) {
		addCookie(response, ROOT_PATH, name, value, ttl);
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
