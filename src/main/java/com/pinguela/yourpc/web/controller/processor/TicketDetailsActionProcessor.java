package com.pinguela.yourpc.web.controller.processor;

import java.io.IOException;

import com.pinguela.YPCException;
import com.pinguela.yourpc.model.Ticket;
import com.pinguela.yourpc.service.TicketService;
import com.pinguela.yourpc.service.impl.TicketServiceImpl;
import com.pinguela.yourpc.web.annotations.ActionProcessor;
import com.pinguela.yourpc.web.constants.Actions;
import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.constants.Views;
import com.pinguela.yourpc.web.exception.InputValidationException;
import com.pinguela.yourpc.web.model.ErrorReport;
import com.pinguela.yourpc.web.util.LocaleUtils;
import com.pinguela.yourpc.web.util.RouterUtils;
import com.pinguela.yourpc.web.util.ValidatorUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ActionProcessor(action = Actions.TICKET_DETAILS)
public class TicketDetailsActionProcessor extends AbstractActionProcessor {
	
	private TicketService service;
	
	public TicketDetailsActionProcessor() {
		service = new TicketServiceImpl();
	}

	@Override
	public void processAction(HttpServletRequest request, HttpServletResponse response, ErrorReport errors)
			throws ServletException, IOException, YPCException, InputValidationException {
		
		Long ticketId = ValidatorUtils.parseLong(request, Parameters.TICKET_ID, request.getParameter(Parameters.TICKET_ID));
		
		Ticket t = service.findById(ticketId, LocaleUtils.getLocale(request));
		request.setAttribute(Attributes.TICKET, t);
		
		RouterUtils.setTargetView(request, Views.TICKET_DETAILS_VIEW);
	}

}
