package com.pinguela.yourpc.web.controller;

import java.io.IOException;

import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.exception.InputValidationException;
import com.pinguela.yourpc.web.model.ErrorReport;
import com.pinguela.yourpc.web.util.SessionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class UserServlet extends YPCServlet {

	@Override
	protected void preProcess(HttpServletRequest req, HttpServletResponse resp, ErrorReport errors)
			throws ServletException, IOException, InputValidationException {
		if (SessionManager.getAttribute(req, Attributes.CUSTOMER) == null) {
			resp.sendError(HttpServletResponse.SC_FORBIDDEN);
		}
	}

	@Override
	protected void postProcess(HttpServletRequest req, HttpServletResponse resp, ErrorReport errors)
			throws ServletException, IOException, InputValidationException {
		// No-op
	}

}
