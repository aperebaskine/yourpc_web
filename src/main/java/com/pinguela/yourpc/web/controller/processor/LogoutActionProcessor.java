package com.pinguela.yourpc.web.controller.processor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinguela.YPCException;
import com.pinguela.yourpc.web.annotations.ActionProcessor;
import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.exception.InputValidationException;
import com.pinguela.yourpc.web.model.ErrorReport;
import com.pinguela.yourpc.web.util.RouterUtils;
import com.pinguela.yourpc.web.util.SessionManager;

@ActionProcessor(action = Parameters.LOGOUT)
public class LogoutActionProcessor extends AbstractActionProcessor {

	@Override
	public void processAction(HttpServletRequest request, HttpServletResponse response, ErrorReport errors)
			throws ServletException, IOException, YPCException, InputValidationException {
		SessionManager.setAttribute(request, Attributes.CUSTOMER, null);
		RouterUtils.callback(request, response);
	}

}
