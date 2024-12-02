package com.pinguela.yourpc.web.controller.processor;

import java.io.IOException;

import com.pinguela.YPCException;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.model.Ticket;
import com.pinguela.yourpc.model.TicketMessage;
import com.pinguela.yourpc.service.TicketMessageService;
import com.pinguela.yourpc.service.impl.TicketMessageServiceImpl;
import com.pinguela.yourpc.web.annotations.ActionProcessor;
import com.pinguela.yourpc.web.constants.Actions;
import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.constants.Views;
import com.pinguela.yourpc.web.controller.TicketServlet;
import com.pinguela.yourpc.web.exception.InputValidationException;
import com.pinguela.yourpc.web.model.ErrorReport;
import com.pinguela.yourpc.web.util.RouterUtils;
import com.pinguela.yourpc.web.util.SessionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ActionProcessor(action = Actions.ADD_TICKET_MESSAGE, servlets = TicketServlet.class)
public class AddTicketMessageActionProcessor extends AbstractActionProcessor {

	private TicketMessageService service;

	public AddTicketMessageActionProcessor() {
		this.service = new TicketMessageServiceImpl();
	}

	@Override
	public void processAction(HttpServletRequest request, HttpServletResponse response, ErrorReport errors)
			throws ServletException, IOException, YPCException, InputValidationException {

		RouterUtils.setTargetView(request, Views.TICKET_DETAILS_VIEW);
		TicketMessage tm = new TicketMessage();

		new ParameterProcessor(request)
		.required(Parameters.TICKET_MESSAGE_TEXT, null, tm::setText);
		
		if (tm.getText() == null) {
			return;
		}

		Customer c = (Customer) SessionManager.getAttribute(request, Attributes.CUSTOMER);
		Ticket t = (Ticket) request.getAttribute(Attributes.TICKET);
		
		tm.setTicketId(t.getId());
		tm.setCustomerId(c.getId());

		service.create(tm);
		request.setAttribute(Attributes.UPDATED, true);
	}

}
