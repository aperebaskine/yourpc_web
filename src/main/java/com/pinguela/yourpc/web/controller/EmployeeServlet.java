package com.pinguela.yourpc.web.controller;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.YPCException;
import com.pinguela.yourpc.model.Employee;
import com.pinguela.yourpc.service.EmployeeService;
import com.pinguela.yourpc.service.impl.EmployeeServiceImpl;
import com.pinguela.yourpc.web.constants.Actions;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.constants.RouteMethod;
import com.pinguela.yourpc.web.constants.Views;
import com.pinguela.yourpc.web.util.RouterUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class EmployeeServlet
 */
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = LogManager.getLogger(EmployeeServlet.class);
	
	private EmployeeService service;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EmployeeServlet() {
		super();
		this.service = new EmployeeServiceImpl();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		String action = request.getParameter(Parameters.ACTION);
		String targetView = null;

		if (Actions.LOGIN.equalsIgnoreCase(action)) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			try {
				Employee e = service.login(username, password);
				if (e == null) {
					logger.warn("Invalid credentials for employee: " + username);
					targetView = Views.USER_LOGIN;
				} else {
					session.setAttribute("employee", e);
					targetView = Views.HOME;
				}
			} catch (YPCException e) {
				logger.error(e.getMessage(), e);
			} 
		}

		RouterUtils.route(request, response, RouteMethod.FORWARD, targetView);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
