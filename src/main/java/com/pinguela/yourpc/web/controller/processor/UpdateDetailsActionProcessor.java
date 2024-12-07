package com.pinguela.yourpc.web.controller.processor;

import java.io.IOException;

import com.pinguela.YPCException;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.service.CustomerService;
import com.pinguela.yourpc.service.impl.CustomerServiceImpl;
import com.pinguela.yourpc.web.annotations.ActionProcessor;
import com.pinguela.yourpc.web.constants.Actions;
import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.constants.Paths;
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

@ActionProcessor(action = Actions.UPDATE_DETAILS, servlets = UserServlet.class)
public class UpdateDetailsActionProcessor extends AbstractActionProcessor {

	private CustomerService service;

	public UpdateDetailsActionProcessor() {
		service = new CustomerServiceImpl();
	}	

	@Override
	public void processAction(HttpServletRequest request, HttpServletResponse response, ErrorReport errors)
			throws ServletException, IOException, YPCException, InputValidationException {

		Customer c = ((Customer) SessionManager.getAttribute(request, Attributes.CUSTOMER)).clone();

		new ParameterProcessor(request)
		.required(Parameters.FIRST_NAME, null, c::setFirstName)
		.required(Parameters.LAST_NAME1, null, c::setLastName1)
		.optional(Parameters.LAST_NAME2, null, c::setLastName2)
		.required(Parameters.DOCUMENT_TYPE_ID, null, c::setDocumentTypeId)
		.required(Parameters.DOCUMENT_NUMBER, null, c::setDocumentNumber)
		.required(Parameters.PHONE_NUMBER, ValidatorUtils::isValidPhoneNumber, c::setPhoneNumber)
		.required(Parameters.EMAIL, ValidatorUtils::isValidEmail, c::setEmail)
		.optional(Parameters.PASSWORD, ValidatorUtils::isValidPassword, c::setUnencryptedPassword)
		.onValidationFailed(() -> RouterUtils.setTargetView(request, Views.UPDATE_DETAILS_VIEW))
		.onValidationSuccessful(() -> {
			
			boolean updated = service.update(c);

			if (updated == false) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} else {
				request.setAttribute(Attributes.UPDATED, true);
				RouterUtils.setTargetView(request, Paths.USER_DETAILS);
			}
		});
	}

}
