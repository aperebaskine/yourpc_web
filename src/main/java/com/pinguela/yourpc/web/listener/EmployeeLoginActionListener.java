package com.pinguela.yourpc.web.listener;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.YPCException;
import com.pinguela.yourpc.model.Employee;
import com.pinguela.yourpc.service.EmployeeService;
import com.pinguela.yourpc.web.constants.Actions;
import com.pinguela.yourpc.web.constants.RouteMethod;
import com.pinguela.yourpc.web.constants.Views;
import com.pinguela.yourpc.web.controller.EmployeeServlet;
import com.pinguela.yourpc.web.model.Route;
import com.pinguela.yourpc.web.util.ServiceManager;
import com.pinguela.yourpc.web.util.SessionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EmployeeLoginActionListener extends AbstractActionListener {

	private static Logger logger = LogManager.getLogger(EmployeeLoginActionListener.class);

	protected EmployeeLoginActionListener() {
		super(Actions.LOGIN, EmployeeServlet.class);
	}

	@Override
	public Route doAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, YPCException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		Employee e = ServiceManager.get(EmployeeService.class).login(username, password);
		
		if (e == null) {
			logger.warn("Invalid credentials for employee: " + username);
			return new Route(Views.USER_LOGIN, RouteMethod.FORWARD);
		} else {
			SessionManager.setAttribute(request, "employee", e);
			return new Route(Views.HOME, RouteMethod.FORWARD);
		}
	}

}
