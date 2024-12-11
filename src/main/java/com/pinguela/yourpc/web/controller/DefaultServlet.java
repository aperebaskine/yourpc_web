package com.pinguela.yourpc.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinguela.yourpc.web.model.ErrorReport;

@SuppressWarnings("serial")
/**
 * Default implementation for YPCServlet. 
 * <p>
 * This servlet processes a form from a request, based on the provided action parameter.
 * </p>
 */
public class DefaultServlet extends YPCServlet {

	@Override
	protected void preProcess(HttpServletRequest req, HttpServletResponse resp, ErrorReport errors)
			throws ServletException, IOException {
		// No-op
	}

	@Override
	protected void postProcess(HttpServletRequest req, HttpServletResponse resp, ErrorReport errors)
			throws ServletException, IOException {
		// No-op
	}

}
