package com.pinguela.yourpc.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinguela.YPCException;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.model.Ticket;
import com.pinguela.yourpc.service.TicketService;
import com.pinguela.yourpc.service.impl.TicketServiceImpl;
import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.exception.InputValidationException;
import com.pinguela.yourpc.web.model.ErrorReport;
import com.pinguela.yourpc.web.util.LocaleUtils;
import com.pinguela.yourpc.web.util.SessionManager;
import com.pinguela.yourpc.web.util.ValidatorUtils;

@SuppressWarnings("serial")
public class TicketServlet extends YPCServlet {
	
	private TicketService ticketService;
	
	public TicketServlet() {
		ticketService = new TicketServiceImpl();
	}

	@Override
	protected void preProcess(HttpServletRequest req, HttpServletResponse resp, ErrorReport errors)
			throws ServletException, IOException, YPCException, InputValidationException {
		
		Long ticketId = ValidatorUtils.parseLong(req, Parameters.TICKET_ID, false);
		
		if (ticketId != null) {
			Ticket t = ticketService.findById(ticketId, LocaleUtils.getLocale(req));
			Customer c = (Customer) SessionManager.getAttribute(req, Attributes.CUSTOMER);
			if (t.getCustomerId() != c.getId()) {
				resp.sendError(HttpServletResponse.SC_FORBIDDEN);
			} else {
				req.setAttribute(Attributes.TICKET, t);
			}
		}
	}

	@Override
	protected void postProcess(HttpServletRequest req, HttpServletResponse resp, ErrorReport errors)
			throws ServletException, IOException, YPCException, InputValidationException {
		
		if (Boolean.TRUE.equals(req.getAttribute(Attributes.UPDATED))) {
			Long ticketId = ((Ticket) req.getAttribute(Attributes.TICKET)).getId();
			Ticket updated = ticketService.findById(ticketId, LocaleUtils.getLocale(req));
			req.setAttribute(Attributes.TICKET, updated);
		}
	}

}
