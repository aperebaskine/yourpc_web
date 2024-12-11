package com.pinguela.yourpc.web.controller.processor;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinguela.YPCException;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.model.CustomerOrder;
import com.pinguela.yourpc.model.RMA;
import com.pinguela.yourpc.model.RMACriteria;
import com.pinguela.yourpc.model.Results;
import com.pinguela.yourpc.model.Ticket;
import com.pinguela.yourpc.model.TicketCriteria;
import com.pinguela.yourpc.service.CustomerOrderService;
import com.pinguela.yourpc.service.ImageFileService;
import com.pinguela.yourpc.service.RMAService;
import com.pinguela.yourpc.service.TicketService;
import com.pinguela.yourpc.service.impl.CustomerOrderServiceImpl;
import com.pinguela.yourpc.service.impl.ImageFileServiceImpl;
import com.pinguela.yourpc.service.impl.RMAServiceImpl;
import com.pinguela.yourpc.service.impl.TicketServiceImpl;
import com.pinguela.yourpc.web.annotations.ActionProcessor;
import com.pinguela.yourpc.web.constants.Actions;
import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.constants.Views;
import com.pinguela.yourpc.web.controller.UserServlet;
import com.pinguela.yourpc.web.exception.InputValidationException;
import com.pinguela.yourpc.web.model.ErrorReport;
import com.pinguela.yourpc.web.util.LocaleUtils;
import com.pinguela.yourpc.web.util.RouterUtils;
import com.pinguela.yourpc.web.util.SessionManager;

@ActionProcessor(action = Actions.USER_DETAILS, servlets = UserServlet.class)
public class UserDetailsActionProcessor extends AbstractActionProcessor {

	private ImageFileService imageFileService;
	private CustomerOrderService customerOrderService;	
	private TicketService ticketService;
	private RMAService rmaService;

	public UserDetailsActionProcessor() {
		imageFileService = new ImageFileServiceImpl();
		customerOrderService = new CustomerOrderServiceImpl();
		ticketService = new TicketServiceImpl();
		rmaService = new RMAServiceImpl();
	}

	@Override
	public void processAction(HttpServletRequest request, HttpServletResponse response, ErrorReport errors)
			throws ServletException, IOException, YPCException, InputValidationException {

		RouterUtils.setTargetView(request, Views.USER_DETAILS_VIEW);

		Locale locale = LocaleUtils.getLocale(request);
		Customer c = (Customer) SessionManager.getAttribute(request, Attributes.CUSTOMER);

		List<String> avatarList = imageFileService.getFilePaths(Attributes.AVATAR, c.getId());
		if (!avatarList.isEmpty()) {

		}

		List<CustomerOrder> orders = customerOrderService.findByCustomer(c.getId(), locale);

		TicketCriteria criteria = new TicketCriteria();
		criteria.setCustomerId(c.getId());
		Results<Ticket> tickets = ticketService.findBy(criteria, locale, 1, 5);

		RMACriteria rmaCriteria = new RMACriteria();
		rmaCriteria.setCustomerId(c.getId());
		List<RMA> rmas = rmaService.findBy(rmaCriteria, locale);

		request.setAttribute(Attributes.CUSTOMER_ORDERS, orders);
		request.setAttribute(Attributes.CUSTOMER_TICKETS, tickets);
		request.setAttribute(Attributes.CUSTOMER_RMAS, rmas);

		request.getRequestDispatcher("/user/UserServlet?action=download-avatar").include(request, response);

	}

}
