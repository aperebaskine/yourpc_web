package com.pinguela.yourpc.web.controller.processor;

import java.io.IOException;

import com.pinguela.YPCException;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.model.Ticket;
import com.pinguela.yourpc.model.TicketMessage;
import com.pinguela.yourpc.service.TicketService;
import com.pinguela.yourpc.service.impl.TicketServiceImpl;
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

@ActionProcessor(action = Actions.INSERT_TICKET, servlets = TicketServlet.class)
public class InsertTicketActionProcessor extends AbstractActionProcessor {

	private TicketService service;

	public InsertTicketActionProcessor() {
		service = new TicketServiceImpl();
	}

	@Override
	public void processAction(HttpServletRequest request, HttpServletResponse response, ErrorReport errors)
			throws ServletException, IOException, YPCException, InputValidationException {

		Customer c = (Customer) SessionManager.getAttribute(request, Attributes.CUSTOMER);
		Ticket t = new Ticket();
		t.setCustomerId(c.getId());
		t.setState("OPN");

		new ParameterProcessor(request)
		.required(Parameters.TYPE, null, t::setType)
		.required(Parameters.TITLE, null, t::setTitle)
		.required(Parameters.DESCRIPTION, null, t::setDescription)
		.optional(Parameters.TICKET_MESSAGE_TEXT, null, (v) -> setMessage(c, t, v))
		.onValidationFailed(() -> RouterUtils.setTargetView(request, Views.NEW_TICKET_VIEW))
		.onValidationSuccessful(() -> {
			Long ticketId = service.create(t);

			if (ticketId == null) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}

			request.setAttribute(Attributes.TICKET, t);
			RouterUtils.setTargetView(request, Views.TICKET_DETAILS_VIEW);
		});


	}

	private void setMessage(Customer c, Ticket t, String message) {
		TicketMessage tm = new TicketMessage();
		tm.setCustomerId(c.getId());
		tm.setText(message);
		t.getMessageList().add(tm);
	}

}
