package com.pinguela.yourpc.web.controller.processor;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pinguela.YPCException;
import com.pinguela.yourpc.model.dto.AttributeDTO;
import com.pinguela.yourpc.service.AttributeService;
import com.pinguela.yourpc.service.impl.AttributeServiceImpl;
import com.pinguela.yourpc.web.constants.Actions;
import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.constants.Cookies;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.controller.AttributeServlet;
import com.pinguela.yourpc.web.model.Route;
import com.pinguela.yourpc.web.util.CookieManager;
import com.pinguela.yourpc.web.util.LocaleUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ActionProcessor(action = Actions.GET_ATTRIBUTES, servlets = AttributeServlet.class)
public class AttributeFetchActionProcessor extends AbstractActionProcessor {
	
	private AttributeService service;
	
	AttributeFetchActionProcessor() {
		service = new AttributeServiceImpl();
	}

	@Override
	public void processAction(HttpServletRequest request, HttpServletResponse response, Route route)
			throws ServletException, IOException, YPCException {
		// TODO Auto-generated method stub
		Short categoryId = Short.valueOf(request.getParameter(Parameters.CATEGORY_ID));
		Boolean returnUnassigned = Boolean.valueOf(request.getParameter(Parameters.RETURN_UNASSIGNED_ATTRIBUTES));
		
		Locale locale = LocaleUtils.toLocale(CookieManager.getValue(request, Cookies.LOCALE));
		
		Map<String, AttributeDTO<?>> attributes = service.findByCategory(
				categoryId, locale, returnUnassigned);
		request.setAttribute(Attributes.ATTRIBUTES, attributes.entrySet());
		
		Gson gson = new GsonBuilder().create();
		
		route.setTargetView("/forms/attribute_fieldset.jsp");
	}

}
