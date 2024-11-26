package com.pinguela.yourpc.web.filter;

import java.io.IOException;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.constants.Headers;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.util.CookieManager;
import com.pinguela.yourpc.web.util.LocaleUtils;
import com.pinguela.yourpc.web.util.SessionManager;
import com.pinguela.yourpc.web.util.URLBuilder;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class LocaleFilter
 */
@SuppressWarnings("serial")
public class LocaleFilter extends HttpFilter implements Filter {

	private static Logger logger = LogManager.getLogger(LocaleFilter.class);

	public LocaleFilter() {
		super();
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpReq = (HttpServletRequest) request;
		Locale locale = null;

		// Check whether the user switched locales
		String newLocaleTag = request.getParameter(Parameters.SWITCH_LOCALE);
		if (newLocaleTag != null) {
			locale = Locale.forLanguageTag(newLocaleTag);
		}

		// Check for the presence of a locale cookie
		if (locale == null) {
			Cookie cookie = CookieManager.getCookie(httpReq, Attributes.LOCALE);
			if (cookie != null && !cookie.getValue().isBlank()) {
				locale = Locale.forLanguageTag(cookie.getValue());
			} 
		}

		// Find supported locales matching HTTP header
		if (locale == null) {
			String header = httpReq.getHeader(Headers.ACCEPT_LANGUAGE);
			String[] languages = header.split(",");

			for (int i = 0; i < languages.length && locale == null; i++) {
				String tag = languages[i].split(";")[0].trim();
				if (LocaleUtils.isSupported(tag)) {
					locale = LocaleUtils.toLocale(tag);
				}
			}
		} 

		// Fall back to default locale
		if (locale == null) {
			locale = LocaleUtils.getDefault();
		}

		// If the locale changed, store it in session and cookie
		if (!locale.equals(SessionManager.getAttribute(httpReq, Attributes.LOCALE))) {
			SessionManager.setAttribute(httpReq, Attributes.LOCALE, locale);
			CookieManager.addCookie((HttpServletResponse) response, Attributes.LOCALE, locale.toLanguageTag());
			logger.info("Setting locale {} for session ID {}", locale.toLanguageTag(), SessionManager.getId(httpReq));
		}

		// Save the parameters so they are kept when switching locale
		request.setAttribute(Attributes.CURRENT_URL, URLBuilder.getParameterizedUrl(httpReq));

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
