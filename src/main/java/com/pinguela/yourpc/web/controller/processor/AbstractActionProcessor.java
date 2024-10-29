package com.pinguela.yourpc.web.listener;

import java.io.IOException;

import com.pinguela.YPCException;
import com.pinguela.yourpc.web.controller.YPCServlet;
import com.pinguela.yourpc.web.model.Route;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class AbstractActionListener {
	
	@SafeVarargs
	protected AbstractActionListener(String action, Class<? extends YPCServlet>... servlets) {
		YPCServlet.registerActionListener(this, action, servlets);
	}
		
	public abstract Route doAction(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, YPCException;

}
