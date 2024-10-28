package com.pinguela.yourpc.web.listener;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.YPCException;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.service.CustomerService;
import com.pinguela.yourpc.web.constants.Actions;
import com.pinguela.yourpc.web.constants.RouteMethod;
import com.pinguela.yourpc.web.constants.Views;
import com.pinguela.yourpc.web.controller.UserServlet;
import com.pinguela.yourpc.web.controller.YPCServlet;
import com.pinguela.yourpc.web.util.RouterUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginActionListener 
extends AbstractRequestActionListener {

	private static Logger logger = LogManager.getLogger(LoginActionListener.class);
	private CustomerService service;

	public LoginActionListener() {
		super(Actions.LOGIN);
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		String targetView = null;

		try {
			Customer c = service.login(email, password);
			if (c == null) {
				logger.warn("Invalid credentials for: " + email);
				targetView = Views.USER_LOGIN;
			} else {
				request.getSession().setAttribute("customer", c);
				targetView = Views.HOME;
			}
		} catch (YPCException e) {
			logger.error(e.getMessage(), e);
		} 

		RouterUtils.route(request, response, RouteMethod.FORWARD, targetView);
	}

	@Override
	public Collection<Class<? extends YPCServlet>> getAssociatedServlets() {
		return Arrays.asList(UserServlet.class);
	}

}
