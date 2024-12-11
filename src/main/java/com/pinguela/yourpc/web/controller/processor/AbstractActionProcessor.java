package com.pinguela.yourpc.web.controller.processor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinguela.YPCException;
import com.pinguela.yourpc.web.annotations.ActionProcessor;
import com.pinguela.yourpc.web.exception.InputValidationException;
import com.pinguela.yourpc.web.model.ErrorReport;

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

	public abstract void processAction(HttpServletRequest request, HttpServletResponse response, ErrorReport errors) 
			throws ServletException, IOException, YPCException, InputValidationException;

}
