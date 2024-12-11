package com.pinguela.yourpc.web.controller.processor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinguela.YPCException;
import com.pinguela.yourpc.web.annotations.ActionProcessor;
import com.pinguela.yourpc.web.constants.Actions;
import com.pinguela.yourpc.web.constants.Views;
import com.pinguela.yourpc.web.controller.TicketServlet;
import com.pinguela.yourpc.web.exception.InputValidationException;
import com.pinguela.yourpc.web.model.ErrorReport;
import com.pinguela.yourpc.web.util.RouterUtils;

@ActionProcessor(action = Actions.TICKET_DETAILS, servlets = TicketServlet.class)
public class TicketDetailsActionProcessor extends AbstractActionProcessor {
	
	@Override
	public void processAction(HttpServletRequest request, HttpServletResponse response, ErrorReport errors)
			throws ServletException, IOException, YPCException, InputValidationException {
		// Ticket already pre-processed
		RouterUtils.setTargetView(request, Views.TICKET_DETAILS_VIEW);
	}

}
