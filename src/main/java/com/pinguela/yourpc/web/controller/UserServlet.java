package com.pinguela.yourpc.web.controller;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.YPCException;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.service.CustomerService;
import com.pinguela.yourpc.service.impl.CustomerServiceImpl;
import com.pinguela.yourpc.web.constants.Actions;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.constants.RouteMethod;
import com.pinguela.yourpc.web.constants.Views;
import com.pinguela.yourpc.web.util.RouterUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class UserServlet
 */
@SuppressWarnings("serial")
@WebServlet("/UserServlet")
public class UserServlet extends YPCServlet {
	
	private static Logger logger = LogManager.getLogger(UserServlet.class);
	
	private CustomerService service;
       
    public UserServlet() {
        super();
        service = new CustomerServiceImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		
		String action = request.getParameter(Parameters.ACTION);
		String targetView = null;
		
		if (Actions.LOGIN.equalsIgnoreCase(action)) {
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			try {
				Customer c = service.login(email, password);
				if (c == null) {
					logger.warn("Invalid credentials for: " + email);
					targetView = Views.USER_LOGIN;
				} else {
					session.setAttribute("customer", c);
					targetView = Views.HOME;
				}
			} catch (YPCException e) {
				logger.error(e.getMessage(), e);
			} 
			
			RouterUtils.route(request, response, RouteMethod.FORWARD, targetView);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
