package com.pinguela.yourpc.web.controller.processor;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.YPCException;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.service.CustomerService;
import com.pinguela.yourpc.web.constants.Actions;
import com.pinguela.yourpc.web.constants.RouteMethod;
import com.pinguela.yourpc.web.constants.Views;
import com.pinguela.yourpc.web.controller.UserServlet;
import com.pinguela.yourpc.web.model.Route;
import com.pinguela.yourpc.web.util.ServiceManager;
import com.pinguela.yourpc.web.util.SessionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserLoginActionProcessor 
extends AbstractActionProcessor {

	private static Logger logger = LogManager.getLogger(UserLoginActionProcessor.class);

	public UserLoginActionProcessor() {
		super(Actions.LOGIN, UserServlet.class);
	}

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response, Route route)
			throws ServletException, IOException, YPCException {

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		Customer c = ServiceManager.get(CustomerService.class).login(email, password);

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
