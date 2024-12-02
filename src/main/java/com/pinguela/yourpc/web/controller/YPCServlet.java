package com.pinguela.yourpc.web.controller;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.YPCException;
import com.pinguela.yourpc.web.constants.HttpErrorCodes;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.controller.processor.AbstractActionProcessor;
import com.pinguela.yourpc.web.exception.InputValidationException;
import com.pinguela.yourpc.web.model.ErrorReport;
import com.pinguela.yourpc.web.util.RouterUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class YPCServlet
 */
@SuppressWarnings("serial")
public abstract class YPCServlet extends HttpServlet {

	private static Logger logger = LogManager.getLogger(YPCServlet.class);

	private static final ActionProcessorDispatcher ACTION_PROCESSOR_DISPATCHER = ActionProcessorDispatcher.getInstance();

	public YPCServlet() {
	}

	@Override
	protected final void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		ErrorReport errors = new ErrorReport();

		try {
			if (!resp.isCommitted()) {
				preProcess(req, resp, errors);
			}

			if (!resp.isCommitted()) {
				processAction(req, resp, errors);
			}

			if (!resp.isCommitted()) {
				postProcess(req, resp, errors);
			}

			if (resp.getStatus() < HttpServletResponse.SC_BAD_REQUEST
					&& RouterUtils.hasRoute(req) && !resp.isCommitted()) {
				RouterUtils.route(req, resp);
			}
		} catch (InputValidationException e) {
			logger.warn(e.getMessage());
			logger.debug(e.getMessage(), e);
			resp.sendError(HttpErrorCodes.SC_UNPROCESSABLE_ENTITY);
		} catch (YPCException e) {
			logger.error(e.getMessage(), e);
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	protected final void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		doGet(req, resp);
	}

	private void processAction(HttpServletRequest req, HttpServletResponse resp, ErrorReport errors) 
			throws ServletException, IOException, YPCException, InputValidationException {

		try {
			String action = (String) req.getParameter(Parameters.ACTION);
			getActionProcessor(action).processAction(req, resp, errors);
		} catch (IllegalArgumentException e) {
			throw new InputValidationException(e.getMessage(), e);
		} 
	}

	private final AbstractActionProcessor getActionProcessor(String action) {
		return ACTION_PROCESSOR_DISPATCHER.dispatch(this, action);
	}

	protected abstract void preProcess(HttpServletRequest req, HttpServletResponse resp, ErrorReport errors)
			throws ServletException, IOException, YPCException, InputValidationException;

	protected abstract void postProcess(HttpServletRequest req, HttpServletResponse resp, ErrorReport errors)
			throws ServletException, IOException, YPCException, InputValidationException;

}
