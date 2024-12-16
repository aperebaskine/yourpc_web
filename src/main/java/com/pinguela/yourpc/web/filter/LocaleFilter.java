package com.pinguela.yourpc.web.filter;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.constants.Cookies;
import com.pinguela.yourpc.web.constants.Headers;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.util.CookieManager;
import com.pinguela.yourpc.web.util.LocaleUtils;
import com.pinguela.yourpc.web.util.RouterUtils;
import com.pinguela.yourpc.web.util.SessionManager;
import com.pinguela.yourpc.web.util.ValidatorUtils;

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
		
		logger.info(Locale.getDefault().toLanguageTag());

		HttpServletRequest httpReq = (HttpServletRequest) request;
		Locale locale = null;
		
		// Check whether the user switched locales
		String switchLocale = request.getParameter(Parameters.SWITCH_LOCALE);
		if (switchLocale != null) {
			locale = Locale.forLanguageTag(switchLocale);
		}

		// Check for the presence of a locale cookie
		if (locale == null) {
			Cookie cookie = CookieManager.getCookie(httpReq, Attributes.LOCALE);
			if (cookie != null && !ValidatorUtils.isBlank(cookie.getValue())) {
				locale = Locale.forLanguageTag(cookie.getValue());
			} 
		}

		// Find supported locales matching HTTP header
		if (locale == null) {
			String header = httpReq.getHeader(Headers.ACCEPT_LANGUAGE);
			
			if (header != null) {
				String[] languages = header.split(",");
				for (int i = 0; i < languages.length && locale == null; i++) {
					String tag = languages[i].split(";")[0].trim();
					if (LocaleUtils.isSupported(tag)) {
						locale = LocaleUtils.toLocale(tag);
					}
				} 
			}
		} 

		// Fall back to default locale
		if (locale == null || !LocaleUtils.isSupported(locale)) {
			locale = LocaleUtils.getDefault();
		}

		// If the locale changed, store it in session and cookie
		if (!locale.toLanguageTag().equals(CookieManager.getValue(httpReq, Cookies.LOCALE))) {
			SessionManager.setAttribute(httpReq, Attributes.LOCALE, locale);
			CookieManager.addCookie((HttpServletResponse) response, Attributes.LOCALE, locale.toLanguageTag());
			logger.info("Setting locale {} for session ID {}", locale.toLanguageTag(), SessionManager.getId(httpReq));

			if (switchLocale != null) {
				RouterUtils.callback(httpReq, (HttpServletResponse) response);
			}
		}

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
