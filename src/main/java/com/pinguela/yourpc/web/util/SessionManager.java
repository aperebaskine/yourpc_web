package com.pinguela.yourpc.web.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionManager {
	
	public static String getId(HttpServletRequest request) {
		return getSession(request).getId();
	}

	public static Object getAttribute(HttpServletRequest request, String name) {
		return getSession(request).getAttribute(name);
	}

	public static void setAttribute(HttpServletRequest request, String name, Object value) {
		getSession(request).setAttribute(name, value);
	}

	public static void removeAttribute(HttpServletRequest request, String name, Object value) {
		getSession(request).removeAttribute(name);
	}

	private static HttpSession getSession(HttpServletRequest request) {
		return request.getSession();
	}

}
