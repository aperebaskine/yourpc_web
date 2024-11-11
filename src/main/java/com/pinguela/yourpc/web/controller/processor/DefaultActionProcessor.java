package com.pinguela.yourpc.web.controller.processor;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.YPCException;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.model.ErrorReport;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Fallback implementation for action processor.
 * 
 * <p>
 * This processor is dispatched for any unrecognized action and sends a 400 Bad Request error.
 * </p>
 */
public final class DefaultActionProcessor extends AbstractActionProcessor {
	
	private static Logger logger = LogManager.getLogger(DefaultActionProcessor.class);
	
	private static final DefaultActionProcessor INSTANCE = new DefaultActionProcessor();
	
	/**
	 * Returns the Singleton instance.
	 * @return The instance
	 */
	public static DefaultActionProcessor getInstance() {
		return INSTANCE;
	}
	
	private DefaultActionProcessor() {
	}

	@Override
	public void processAction(HttpServletRequest request, HttpServletResponse response, ErrorReport errors)
			throws ServletException, IOException, YPCException {
		logger.warn("Request received at %s contains unrecognized action: %s",
				request.getRequestURI(), request.getParameter(Parameters.ACTION));
		response.sendError(HttpServletResponse.SC_BAD_REQUEST);
	}

}
