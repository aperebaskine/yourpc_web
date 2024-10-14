package com.pinguela.yourpc.web.util;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RouterUtils {
	
	private static Logger logger = LogManager.getLogger(RouterUtils.class);
	
	public static final void route(HttpServletRequest request, HttpServletResponse response,
			boolean forwardOrRedirect, String targetview) 
	throws IOException, ServletException {
		
	}

}
