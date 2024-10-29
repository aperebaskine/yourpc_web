package com.pinguela.yourpc.web.controller.processor;

import java.io.IOException;

import com.pinguela.YPCException;
import com.pinguela.yourpc.web.controller.YPCServlet;
import com.pinguela.yourpc.web.model.Route;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class AbstractActionProcessor {
	
	@SafeVarargs
	protected AbstractActionProcessor(String action, Class<? extends YPCServlet>... servlets) {
		YPCServlet.registerActionProcessor(this, action, servlets);
	}
		
	public abstract void doAction(HttpServletRequest request, HttpServletResponse response, Route route) 
			throws ServletException, IOException, YPCException;

}
