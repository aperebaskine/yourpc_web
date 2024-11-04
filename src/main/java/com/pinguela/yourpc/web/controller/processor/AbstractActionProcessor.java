package com.pinguela.yourpc.web.controller.processor;

import java.io.IOException;

import com.pinguela.YPCException;
import com.pinguela.yourpc.web.model.Route;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Handles a specific action from an HTTP request within the context of one (or more) servlets. 
 * <p>
 * Subclasses <b>must</b> be annotated
 * with @{@link ActionProcessor} specifying the action name and associated servlets, and <b>must</b> provide a 
 * package-private, no-argument constructor. Registration is handled automatically.
 * </p>
 */
public abstract class AbstractActionProcessor {

	protected AbstractActionProcessor() {	
	}

	public abstract void processAction(HttpServletRequest request, HttpServletResponse response, Route route) 
			throws ServletException, IOException, YPCException;

}
