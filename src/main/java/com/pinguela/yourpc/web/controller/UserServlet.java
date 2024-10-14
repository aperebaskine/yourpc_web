package com.pinguela.yourpc.web.controller;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.YPCException;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.service.CustomerService;
import com.pinguela.yourpc.service.impl.CustomerServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class UserServlet
 */
@SuppressWarnings("serial")
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	
	private static Logger logger = LogManager.getLogger(UserServlet.class);
	
	private CustomerService service;
       
    public UserServlet() {
        super();
        service = new CustomerServiceImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		
		String action = request.getParameter("action");
		String targetView = null;
		
		if ("login".equalsIgnoreCase(action)) {
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			try {
				Customer c = service.login(email, password);
				if (c == null) {
					logger.warn("Invalid credentials for: " + email);
				} else {
					session.setAttribute("customer", c);
					targetView = "/index.jsp";
				}
			} catch (YPCException e) {
				logger.error(e.getMessage(), e);
			} 
		}
		request.getRequestDispatcher(targetView).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
