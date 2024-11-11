package com.pinguela.yourpc.web.controller.processor;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pinguela.YPCException;
import com.pinguela.yourpc.model.dto.AttributeDTO;
import com.pinguela.yourpc.service.AttributeService;
import com.pinguela.yourpc.service.impl.AttributeServiceImpl;
import com.pinguela.yourpc.web.annotations.ActionProcessor;
import com.pinguela.yourpc.web.constants.Actions;
import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.model.ErrorReport;
import com.pinguela.yourpc.web.util.CookieManager;
import com.pinguela.yourpc.web.util.LocaleUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ActionProcessor(action = Actions.GET_ATTRIBUTES)
public class AttributeFetchActionProcessor extends AbstractActionProcessor {
	
	private static Logger logger = LogManager.getLogger(AttributeFetchActionProcessor.class);
	
	private AttributeService service;
	
	public AttributeFetchActionProcessor() {
		service = new AttributeServiceImpl();
	}

	@Override
	public void processAction(HttpServletRequest request, HttpServletResponse response, ErrorReport errors)
			throws ServletException, IOException, YPCException {
		// TODO Auto-generated method stub
		Short categoryId = Short.valueOf(request.getParameter(Parameters.CATEGORY_ID));
		Boolean returnUnassigned = Boolean.valueOf(request.getParameter(Parameters.RETURN_UNASSIGNED_ATTRIBUTES));
		
		Locale locale = LocaleUtils.toLocale(CookieManager.getValue(request, Attributes.LOCALE));
		
		Map<String, AttributeDTO<?>> attributes = 
				service.findByCategory(categoryId, locale, returnUnassigned);
		
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(attributes.values());
		
		logger.debug("Results of product attribute request: {}", ToStringBuilder.reflectionToString(attributes.values()));
		request.setAttribute("productAttributes", json);
	}

}
