package com.pinguela.yourpc.web.filter;

import java.io.IOException;

import com.pinguela.yourpc.web.constants.Headers;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class LocaleFilter
 */
@SuppressWarnings("serial")
public class LocaleFilter extends HttpFilter implements Filter {

	public LocaleFilter() {
		super();
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		
		String header = ((HttpServletRequest) request).getHeader(Headers.ACCEPT_LANGUAGE);

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
