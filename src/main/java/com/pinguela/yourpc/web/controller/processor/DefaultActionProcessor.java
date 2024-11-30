package com.pinguela.yourpc.web.controller.processor;

import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.exception.InputValidationException;
import com.pinguela.yourpc.web.model.ErrorReport;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Fallback implementation for action processor.
 * 
 * <p>
 * This processor is dispatched for any unrecognized action and throws an {@link InputValidationException}.
 * </p>
 */
public final class DefaultActionProcessor extends AbstractActionProcessor {
		
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
			throws InputValidationException {
		throw new InputValidationException(String.format("Request received at %s contains unrecognized action: %s",
				request.getRequestURI(), request.getParameter(Parameters.ACTION)));
	}

}
