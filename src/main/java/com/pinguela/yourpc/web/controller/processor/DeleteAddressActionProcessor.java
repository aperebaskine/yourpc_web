package com.pinguela.yourpc.web.controller.processor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinguela.YPCException;
import com.pinguela.yourpc.service.AddressService;
import com.pinguela.yourpc.service.impl.AddressServiceImpl;
import com.pinguela.yourpc.web.annotations.ActionProcessor;
import com.pinguela.yourpc.web.constants.Actions;
import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.controller.UserServlet;
import com.pinguela.yourpc.web.exception.InputValidationException;
import com.pinguela.yourpc.web.model.ErrorReport;
import com.pinguela.yourpc.web.util.ValidatorUtils;

@ActionProcessor(action = Actions.DELETE_ADDRESS, servlets = UserServlet.class)
public class DeleteAddressActionProcessor
extends AbstractActionProcessor {

	private AddressService service = new AddressServiceImpl();
	
	@Override
	public void processAction(HttpServletRequest request, HttpServletResponse response, ErrorReport errors)
			throws ServletException, IOException, YPCException, InputValidationException {
		
		Integer addressId = 
				ValidatorUtils.parseInt(request, Parameters.ADDRESS_ID, request.getParameter(Parameters.ADDRESS_ID));
		
		if (service.delete(addressId)) {
			request.setAttribute(Attributes.UPDATED, true);
		}
		
		request.setAttribute(Attributes.CALLBACK, true);
	}

}
