package com.pinguela.yourpc.web.util;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.constants.RouteMethod;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RouterUtils {

	private static Logger logger = LogManager.getLogger(RouterUtils.class);
	
	public static final boolean hasRoute(ServletRequest request) {
		return getTargetView(request) != null;
	}
	
	public static final String getTargetView(ServletRequest request) {
		return (String) request.getAttribute(Attributes.TARGET_VIEW);
	}
	
	public static final boolean isTargetViewSet(ServletRequest request) {
		return request.getAttribute(Attributes.TARGET_VIEW) != null;
	}
	
	public static final void setTargetView(ServletRequest request, String targetView) {
		request.setAttribute(Attributes.TARGET_VIEW, targetView);
	}
	
	public static final RouteMethod getRouteMethod(ServletRequest request) {
		return (RouteMethod) request.getAttribute(Attributes.ROUTE_METHOD);
	}
	
	public static final void setRouteMethod(ServletRequest request, RouteMethod routeMethod) {
		request.setAttribute(Attributes.ROUTE_METHOD, routeMethod);
	}
	
	public static final void setRoute(ServletRequest request, String targetView, RouteMethod routeMethod) {
		setTargetView(request, targetView);
		setRouteMethod(request, routeMethod);
	}
	
	public static final void route(HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException {
		
		String targetView = (String) request.getAttribute(Attributes.TARGET_VIEW);
		RouteMethod routeMethod = (RouteMethod) request.getAttribute(Attributes.ROUTE_METHOD);
		
		if (targetView == null) {
			throw new IllegalStateException("No target view set for this request.");
		}
		
		if (routeMethod == null) {
			routeMethod = RouteMethod.FORWARD;
		}

		switch (routeMethod) {
		case FORWARD:
			logger.info("Forwarding to {}...", targetView);
			request.getRequestDispatcher(targetView).forward(request, response);
			break;
		case REDIRECT:
			logger.info("Redirecting to {}...", targetView);
			response.sendRedirect(new StringBuilder(request.getContextPath()).append(targetView).toString());
			break;
		}
	}
	
	public static final void route(HttpServletRequest request, HttpServletResponse response, String targetView) 
			throws IOException, ServletException {
		setTargetView(request, targetView);
		route(request, response);
	}

	public static final void route(HttpServletRequest request, 
			HttpServletResponse response, String targetView, RouteMethod routeMethod) 
			throws IOException, ServletException {
		setRouteMethod(request, routeMethod);
		route(request, response, targetView);
	}
	
	public static final void callback(HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException {
		
		String callbackUrl = request.getParameter(Parameters.CALLBACK_URL);
		if (callbackUrl == null) {
			callbackUrl = (String) request.getAttribute(Attributes.CURRENT_URL);
		}
		
		response.sendRedirect(callbackUrl);
	}
	
}
