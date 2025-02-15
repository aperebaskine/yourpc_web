package com.pinguela.yourpc.web.controller.processor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinguela.YPCException;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.service.CustomerService;
import com.pinguela.yourpc.service.impl.CustomerServiceImpl;
import com.pinguela.yourpc.web.annotations.ActionProcessor;
import com.pinguela.yourpc.web.constants.Actions;
import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.constants.Views;
import com.pinguela.yourpc.web.controller.DefaultServlet;
import com.pinguela.yourpc.web.exception.InputValidationException;
import com.pinguela.yourpc.web.model.ErrorReport;
import com.pinguela.yourpc.web.util.RouterUtils;
import com.pinguela.yourpc.web.util.SessionManager;
import com.pinguela.yourpc.web.util.ValidatorUtils;

@ActionProcessor(action = Actions.REGISTER, servlets = DefaultServlet.class)
public class RegisterActionProcessor extends AbstractActionProcessor {
	
	private CustomerService service;
	
	public RegisterActionProcessor() {
		this.service = new CustomerServiceImpl();
	}

	@Override
	public void processAction(HttpServletRequest request, HttpServletResponse response, ErrorReport errors)
			throws ServletException, IOException, YPCException, InputValidationException {
		
		Customer c = new Customer();
		
		new ParameterProcessor(request)
		.required(Parameters.FIRST_NAME, null, c::setFirstName)
		.required(Parameters.LAST_NAME1, null, c::setLastName1)
		.optional(Parameters.LAST_NAME2, null, c::setLastName2)
		.required(Parameters.DOCUMENT_TYPE_ID, null, c::setDocumentTypeId)
		.required(Parameters.DOCUMENT_NUMBER, null, c::setDocumentNumber)
		.required(Parameters.PHONE_NUMBER, ValidatorUtils::isValidPhoneNumber, c::setPhoneNumber)
		.required(Parameters.EMAIL, ValidatorUtils::isValidEmail, c::setEmail)
		.required(Parameters.PASSWORD, ValidatorUtils::isValidPassword, c::setUnencryptedPassword)
		.onValidationFailed(() -> RouterUtils.setTargetView(request, Views.REGISTER_VIEW))
		.onValidationSuccessful(() -> {
			Integer id = service.register(c);
			
			if (id == null) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} else {
				SessionManager.setAttribute(request, Attributes.CUSTOMER, c);
				RouterUtils.setTargetView(request, Views.USER_DETAILS_VIEW);
			}
		});
	}

}
