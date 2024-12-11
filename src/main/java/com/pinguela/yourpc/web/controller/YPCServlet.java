package com.pinguela.yourpc.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.YPCException;
import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.constants.HttpErrorCodes;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.controller.processor.AbstractActionProcessor;
import com.pinguela.yourpc.web.exception.InputValidationException;
import com.pinguela.yourpc.web.model.ErrorReport;
import com.pinguela.yourpc.web.util.RouterUtils;

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
		req.setAttribute(Attributes.ERRORS, errors);

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

			if (resp.getStatus() < HttpServletResponse.SC_BAD_REQUEST  && !resp.isCommitted()) {
				route(req, resp);
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

	protected void route(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		if (Boolean.TRUE.equals(request.getAttribute(Attributes.CALLBACK))) {
			RouterUtils.callback(request, response);
		} else if (RouterUtils.hasRoute(request)) {
			RouterUtils.route(request, response);
		}
	}

	protected String getAction(HttpServletRequest req) throws ServletException, IOException {
		return req.getParameter(Parameters.ACTION);
	}

	private void processAction(HttpServletRequest req, HttpServletResponse resp, ErrorReport errors) 
			throws ServletException, IOException, YPCException, InputValidationException {

		try {
			String action = getAction(req);
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
