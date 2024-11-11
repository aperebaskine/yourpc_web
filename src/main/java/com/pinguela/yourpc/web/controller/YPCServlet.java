package com.pinguela.yourpc.web.controller;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.YPCException;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.controller.processor.AbstractActionProcessor;
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

		if (!resp.isCommitted()) {
			preProcess(req, resp, errors);
		}

		if (!resp.isCommitted()) {
			processAction(req, resp, errors);
		}

		if (!resp.isCommitted()) {
			postProcess(req, resp, errors);
		}

		if (resp.getStatus() < HttpServletResponse.SC_BAD_REQUEST) {
			RouterUtils.route(req, resp);
		}
	}

	@Override
	protected final void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		doGet(req, resp);
	}

	private void processAction(HttpServletRequest req, HttpServletResponse resp, ErrorReport errors) 
			throws ServletException, IOException {

		String action = (String) req.getParameter(Parameters.ACTION);

		try {
			getActionProcessor(action).processAction(req, resp, errors);
		} catch (YPCException e) {
			logger.error(e.getMessage(), e);
			throw new ServletException(e);
		}
	}

	private final AbstractActionProcessor getActionProcessor(String action) {
		return ACTION_PROCESSOR_DISPATCHER.dispatch(this, action);
	}

	protected abstract void preProcess(HttpServletRequest req, HttpServletResponse resp, ErrorReport errors)
			throws ServletException, IOException;

	protected abstract void postProcess(HttpServletRequest req, HttpServletResponse resp, ErrorReport errors)
			throws ServletException, IOException;

}
