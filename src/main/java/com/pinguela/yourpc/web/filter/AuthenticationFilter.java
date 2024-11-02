package com.pinguela.yourpc.web.filter;

import java.io.IOException;

import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.constants.RouteMethod;
import com.pinguela.yourpc.web.constants.Views;
import com.pinguela.yourpc.web.model.Route;
import com.pinguela.yourpc.web.util.RouterUtils;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class AuthenticationFilter
 */
@SuppressWarnings("serial")
public abstract class AuthenticationFilter extends HttpFilter implements Filter {

	protected AuthenticationFilter() {
		super();
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		Customer c = (Customer) request.getAttribute(Attributes.CUSTOMER);

		if (c == null) {
			RouterUtils.route((HttpServletRequest) request, 
					(HttpServletResponse) response, new Route(Views.USER_LOGIN, RouteMethod.REDIRECT));
		}

		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
