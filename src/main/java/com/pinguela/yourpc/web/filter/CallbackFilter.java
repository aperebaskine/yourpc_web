package com.pinguela.yourpc.web.filter;

import java.io.IOException;

import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.util.URLBuilder;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class URLBuilderFilter
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
