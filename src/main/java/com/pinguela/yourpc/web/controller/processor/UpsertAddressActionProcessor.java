package com.pinguela.yourpc.web.controller.processor;

import java.io.IOException;

import com.pinguela.YPCException;
import com.pinguela.yourpc.model.Address;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.service.AddressService;
import com.pinguela.yourpc.service.impl.AddressServiceImpl;
import com.pinguela.yourpc.web.annotations.ActionProcessor;
import com.pinguela.yourpc.web.constants.Actions;
import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.constants.Views;
import com.pinguela.yourpc.web.controller.UserServlet;
import com.pinguela.yourpc.web.exception.InputValidationException;
import com.pinguela.yourpc.web.model.ErrorReport;
import com.pinguela.yourpc.web.util.RouterUtils;
import com.pinguela.yourpc.web.util.SessionManager;
import com.pinguela.yourpc.web.util.ValidatorUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ActionProcessor(action = Actions.UPSERT_ADDRESS, servlets = UserServlet.class)
public class UpsertAddressActionProcessor extends AbstractActionProcessor {

	private AddressService service;

	public UpsertAddressActionProcessor() {
		this.service = new AddressServiceImpl();
	}

	@Override
	public void processAction(HttpServletRequest request, HttpServletResponse response, ErrorReport errors)
			throws ServletException, IOException, YPCException, InputValidationException {

		Address old = (Address) request.getAttribute(Attributes.ADDRESS);
		Address a = new Address();
		
		Customer c = (Customer) SessionManager.getAttribute(request, Attributes.CUSTOMER);
		a.setCustomerId(c.getId());
		if (old != null) {
			a.setCreationDate(old.getCreationDate());
		}

		new ParameterProcessor(request)
		.optional(Parameters.ADDRESS_ID, ValidatorUtils::parseInt, null, a::setId)
		.required(Parameters.STREET_NAME, null, a::setStreetName)
		.optional(Parameters.STREET_NUMBER, ValidatorUtils::parseShort, null, a::setStreetNumber)
		.optional(Parameters.FLOOR, ValidatorUtils::parseShort, null, a::setFloor)
		.optional(Parameters.DOOR, null, a::setDoor)
		.required(Parameters.ZIP_CODE, null, a::setZipCode)
		.required(Parameters.CITY, ValidatorUtils::parseInt, null, a::setCityId)
		.optional(Parameters.IS_DEFAULT, ValidatorUtils::parseBoolean, null, a::setIsDefault)
		.optional(Parameters.IS_BILLING, ValidatorUtils::parseBoolean, null, a::setIsBilling)
		.onValidationFailed(() -> RouterUtils.setTargetView(request, a.getId() == null ? Views.NEW_ADDRESS_VIEW : Views.EDIT_ADDRESS_VIEW))
		.onValidationSuccessful(() -> {

			Integer id = null;

			if (a.getId() != null) {
				id = service.update(a);
			} else {
				id = service.create(a);
			}

			if (id == null) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} else {
				request.setAttribute(Attributes.UPDATED, true);
				request.setAttribute(Attributes.CALLBACK, true);
			}
		});
	}

}
