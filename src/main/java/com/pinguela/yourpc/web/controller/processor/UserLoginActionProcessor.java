package com.pinguela.yourpc.web.controller.processor;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.YPCException;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.service.CustomerService;
import com.pinguela.yourpc.service.impl.CustomerServiceImpl;
import com.pinguela.yourpc.web.constants.Actions;
import com.pinguela.yourpc.web.constants.RouteMethod;
import com.pinguela.yourpc.web.constants.Views;
import com.pinguela.yourpc.web.controller.UserServlet;
import com.pinguela.yourpc.web.model.Route;
import com.pinguela.yourpc.web.util.SessionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ActionProcessor(action = Actions.LOGIN, servlets = UserServlet.class)
public class UserLoginActionProcessor 
extends AbstractActionProcessor {

	private static Logger logger = LogManager.getLogger(UserLoginActionProcessor.class);
	
	private CustomerService service;

	public UserLoginActionProcessor() {
		service = new CustomerServiceImpl();
	}

	@Override
	public void processAction(HttpServletRequest request, HttpServletResponse response, Route route)
			throws ServletException, IOException, YPCException {

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		Customer c = service.login(email, password);

		if (c == null) {
			logger.warn("Invalid credentials for: " + email);
			route.setTargetView(Views.USER_LOGIN);
			route.setRouteMethod(RouteMethod.REDIRECT);
		} else {
			SessionManager.setAttribute(request, "customer", c);
			route.setTargetView(Views.HOME);
			route.setRouteMethod(RouteMethod.FORWARD);
		}
	}

}
