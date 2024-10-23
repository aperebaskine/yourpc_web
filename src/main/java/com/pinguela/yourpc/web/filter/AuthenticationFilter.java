package com.pinguela.yourpc.web.filter;

import java.io.IOException;

import com.pinguela.yourpc.model.AbstractPerson;
import com.pinguela.yourpc.web.constants.RouteMethod;
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
public abstract class AuthenticationFilter<T extends AbstractPerson> extends HttpFilter implements Filter {
	
	private Class<T> targetClass;
	private String loginViewPath;
	
    /**
     * @see HttpFilter#HttpFilter()
     */
    protected AuthenticationFilter(Class<T> targetClass, String loginViewPath) {
        super();
        this.targetClass = targetClass;
        this.loginViewPath = loginViewPath;
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		
		@SuppressWarnings("unchecked")
		T person = (T) request.getAttribute(targetClass.getName());
		
		if (person == null) {
			RouterUtils.route((HttpServletRequest) request, (HttpServletResponse) response, RouteMethod.REDIRECT, loginViewPath);
		}

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
