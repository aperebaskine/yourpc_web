package com.pinguela.yourpc.web.controller;

import java.io.IOException;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.YPCException;
import com.pinguela.yourpc.model.Address;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.service.AddressService;
import com.pinguela.yourpc.service.CustomerService;
import com.pinguela.yourpc.service.impl.AddressServiceImpl;
import com.pinguela.yourpc.service.impl.CustomerServiceImpl;
import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.exception.InputValidationException;
import com.pinguela.yourpc.web.model.ErrorReport;
import com.pinguela.yourpc.web.util.SessionManager;
import com.pinguela.yourpc.web.util.ValidatorUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class UserServlet extends YPCServlet {
	
	private CustomerService customerService;
	private AddressService addressService;
	
	public UserServlet() {
		this.customerService = new CustomerServiceImpl();
		this.addressService = new AddressServiceImpl();
	}

	@Override
	protected void preProcess(HttpServletRequest req, HttpServletResponse resp, ErrorReport errors)
			throws ServletException, IOException, InputValidationException {
		if (SessionManager.getAttribute(req, Attributes.CUSTOMER) == null) {
			resp.sendError(HttpServletResponse.SC_FORBIDDEN);
		}
		
		String addressIdStr = req.getParameter(Parameters.ADDRESS_ID);
		if (addressIdStr != null) {
			try {
				Integer addressId = ValidatorUtils.parseInt(req, Parameters.ADDRESS_ID, addressIdStr);
				Address a = addressService.findById(addressId);
				Customer c = (Customer) SessionManager.getAttribute(req, Attributes.CUSTOMER);
				
				if (!c.getId().equals(a.getCustomerId())) {
					resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
				} else {
					req.setAttribute(Attributes.ADDRESS, a);
				}
			} catch (YPCException e) {
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		}
	}

	@Override
	protected void postProcess(HttpServletRequest req, HttpServletResponse resp, ErrorReport errors)
			throws ServletException, IOException, InputValidationException {
		if (Boolean.TRUE.equals(req.getAttribute(Attributes.UPDATED))) {
			Customer old = (Customer) SessionManager.getAttribute(req, Attributes.CUSTOMER);
			try {
				SessionManager.setAttribute(req, Attributes.CUSTOMER, customerService.findById(old.getId()));
			} catch (ServiceException | DataException e) {
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		}
	}

}
