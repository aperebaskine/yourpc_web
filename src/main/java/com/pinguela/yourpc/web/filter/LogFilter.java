package com.pinguela.yourpc.web.filter;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class AuthenticationFilter
 */
@SuppressWarnings("serial")
@WebFilter("/*")
public class LogFilter extends HttpFilter implements Filter {
	
	private static Logger logger = LogManager.getLogger(LogFilter.class);
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public LogFilter() {
        super();
        // TODO Auto-generated constructor stub
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

		// pass the request along the filter chain
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		StringBuilder url = new StringBuilder();
		url.append(httpRequest.getRequestURL());
//		url.append(httpRequest.getScheme())
//		.append(httpRequest.getLocalAddr())
//		.append(httpRequest.getLocalPort())
//		.append(httpRequest.getContextPath())
//		.append(httpRequest.getRequestURI());
		
		logger.info("--> Request " +url +" from " +httpRequest.getRemoteAddr());
		
		chain.doFilter(request, response);
		logger.info("Request " +url +" from " +httpRequest.getRemoteAddr() +" -->");
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}