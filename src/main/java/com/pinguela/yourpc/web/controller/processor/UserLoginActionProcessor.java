package com.pinguela.yourpc.web.controller.processor;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.InvalidLoginCredentialsException;
import com.pinguela.YPCException;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.service.CustomerService;
import com.pinguela.yourpc.service.impl.CustomerServiceImpl;
import com.pinguela.yourpc.web.annotations.ActionProcessor;
import com.pinguela.yourpc.web.constants.Actions;
import com.pinguela.yourpc.web.constants.ErrorCodes;
import com.pinguela.yourpc.web.constants.RouteMethod;
import com.pinguela.yourpc.web.constants.Views;
import com.pinguela.yourpc.web.model.ErrorReport;
import com.pinguela.yourpc.web.util.RouterUtils;
import com.pinguela.yourpc.web.util.SessionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ActionProcessor(action = Actions.LOGIN)
public class UserLoginActionProcessor 
extends AbstractActionProcessor {

	private static Logger logger = LogManager.getLogger(UserLoginActionProcessor.class);

	private CustomerService service;

	public UserLoginActionProcessor() {
		service = new CustomerServiceImpl();
	}

	@Override
	public void processAction(HttpServletRequest request, HttpServletResponse response, ErrorReport errors)
			throws ServletException, IOException, YPCException {

		String email = request.getParameter("email").trim();
		String password = request.getParameter("password").trim();

		Customer c = null;

		try {
			c = service.login(email, password);
			SessionManager.setAttribute(request, "customer", c);
			RouterUtils.setRoute(request, Views.HOME, RouteMethod.FORWARD);		
			
		} catch (InvalidLoginCredentialsException e) {
			logger.warn("Invalid credentials for: " + email);
			errors.addGlobalError(ErrorCodes.INVALID_LOGIN);
			RouterUtils.setRoute(request, Views.USER_LOGIN, RouteMethod.REDIRECT);
		} 
	}

}
