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
import javax.servlet.http.HttpServletResponse;

import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.constants.RouteMethod;
import com.pinguela.yourpc.web.constants.Views;
import com.pinguela.yourpc.web.util.RouterUtils;
import com.pinguela.yourpc.web.util.SessionManager;

/**
 * Servlet Filter implementation class AuthenticationFilter
 */
@SuppressWarnings("serial")
public class AuthenticationFilter extends HttpFilter implements Filter {

	public AuthenticationFilter() {
		super();
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		Customer c = (Customer) SessionManager.getAttribute((HttpServletRequest) request, Attributes.CUSTOMER);

		if (c == null) {
			RouterUtils.route((HttpServletRequest) request, 
					(HttpServletResponse) response, Views.USER_LOGIN_VIEW, RouteMethod.REDIRECT);
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
