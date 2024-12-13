package com.pinguela.yourpc.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinguela.YPCException;
import com.pinguela.yourpc.web.exception.InputValidationException;
import com.pinguela.yourpc.web.model.ErrorReport;

@SuppressWarnings("serial")
public class WSServlet extends YPCServlet {

	@Override
	protected void preProcess(HttpServletRequest req, HttpServletResponse resp, ErrorReport errors)
			throws ServletException, IOException, YPCException, InputValidationException {
		// TODO Auto-generated method stub
		resp.setContentType("application/json");
	}

	@Override
	protected void postProcess(HttpServletRequest req, HttpServletResponse resp, ErrorReport errors)
			throws ServletException, IOException, YPCException, InputValidationException {
		// TODO Auto-generated method stub
		resp.getOutputStream().flush();
	}

}
