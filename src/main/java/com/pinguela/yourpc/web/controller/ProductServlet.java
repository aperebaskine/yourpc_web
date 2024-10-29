package com.pinguela.yourpc.web.controller;

import java.io.IOException;

import com.pinguela.yourpc.web.model.Route;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProductServlet
 */
@SuppressWarnings("serial")
public class ProductServlet extends YPCServlet {
	
	public ProductServlet() {
	}
	
	@Override
	protected void preProcess(HttpServletRequest req, HttpServletResponse resp, Route route)
			throws ServletException, IOException {	
	}
	
	@Override
	protected void postProcess(HttpServletRequest req, HttpServletResponse resp, Route route)
			throws ServletException, IOException {	
	}

}
