package com.pinguela.yourpc.web.util;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.yourpc.web.model.Route;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RouterUtils {

	private static Logger logger = LogManager.getLogger(RouterUtils.class);

	public static final void route(HttpServletRequest request, HttpServletResponse response, Route route) 
			throws IOException, ServletException {

		String targetView = route.getTargetView();

		switch (route.getRouteMethod()) {
		case FORWARD:
			logger.info("Forwarding to {}...", targetView);
			request.getRequestDispatcher(targetView).forward(request, response);
		case REDIRECT:
			logger.info("Redirecting to {}...", targetView);
			response.sendRedirect(new StringBuilder(request.getContextPath()).append(targetView).toString());
		}
	}

}
