package com.pinguela.yourpc.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;

import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.util.URLBuilder;

/**
 * Stores request URL as an attribute for callback purposes.
 */
@SuppressWarnings("serial")
public class CallbackFilter extends HttpFilter implements Filter {

	public CallbackFilter() {
		super();
	}

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (request instanceof HttpServletRequest) {
			request.setAttribute(Attributes.CURRENT_URL, URLBuilder.getParameterizedUrl((HttpServletRequest) request));
		}

		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
