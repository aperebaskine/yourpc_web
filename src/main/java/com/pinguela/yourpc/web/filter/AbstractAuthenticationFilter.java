package com.pinguela.yourpc.web.filter;

import java.io.IOException;

import com.pinguela.yourpc.model.AbstractPerson;
import com.pinguela.yourpc.web.constants.RouteMethod;
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
public abstract class AbstractAuthenticationFilter<T extends AbstractPerson> extends HttpFilter implements Filter {
	
	private String targetEntityName;
	private String loginViewPath;
	
    /**
     * @see HttpFilter#HttpFilter()
     */
    protected AbstractAuthenticationFilter(Class<T> targetClass, String loginViewPath) {
        super();
        this.targetEntityName = getTargetEntityName(targetClass);
        this.loginViewPath = loginViewPath;
    }
    
    private static <T extends AbstractPerson> String getTargetEntityName(Class<T> target) {
    	StringBuilder sb = new StringBuilder(target.getSimpleName());
    	sb.replace(0, 1, String.valueOf(Character.toLowerCase(sb.charAt(0))));
    	return sb.toString();
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
		T person = (T) request.getAttribute(targetEntityName);
		
		if (person == null) {
			RouterUtils.route((HttpServletRequest) request, (HttpServletResponse) response, new Route(loginViewPath, RouteMethod.REDIRECT));
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
