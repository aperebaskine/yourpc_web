package com.pinguela.yourpc.web.controller.processor;

import java.io.IOException;

import com.pinguela.YPCException;
import com.pinguela.yourpc.web.model.Route;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class AbstractActionProcessor {
	
	protected AbstractActionProcessor() {	
	}
		
	public abstract void processAction(HttpServletRequest request, HttpServletResponse response, Route route) 
			throws ServletException, IOException, YPCException;

}
