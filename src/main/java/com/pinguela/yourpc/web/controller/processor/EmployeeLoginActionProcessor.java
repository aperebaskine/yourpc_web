package com.pinguela.yourpc.web.controller.processor;

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

public class EmployeeLoginActionProcessor extends AbstractActionProcessor {

	private static Logger logger = LogManager.getLogger(EmployeeLoginActionProcessor.class);

	protected EmployeeLoginActionProcessor() {
		super(Actions.LOGIN, EmployeeServlet.class);
	}

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response, Route route)
			throws ServletException, IOException, YPCException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		Employee e = ServiceManager.get(EmployeeService.class).login(username, password);
		
		if (e == null) {
			logger.warn("Invalid credentials for employee: " + username);
			route.setTargetView(Views.EMPLOYEE_LOGIN);
			route.setRouteMethod(RouteMethod.FORWARD);
		} else {
			SessionManager.setAttribute(request, "employee", e);
			route.setTargetView(Views.HOME);
			route.setRouteMethod(RouteMethod.FORWARD);
		}
	}

}
