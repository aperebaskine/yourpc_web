package com.pinguela.yourpc.web.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pinguela.yourpc.model.dto.AttributeDTO;
import com.pinguela.yourpc.service.AttributeService;
import com.pinguela.yourpc.service.impl.AttributeServiceImpl;
import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.util.CookieManager;
import com.pinguela.yourpc.web.util.LocaleUtils;

@SuppressWarnings("serial")
public class AttributeServlet extends HttpServlet {
	
	private static Logger logger = LogManager.getLogger(AttributeServlet.class);
	
	private AttributeService service;
	
	public AttributeServlet() {
		this.service = new AttributeServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Short categoryId = Short.valueOf(req.getParameter(Parameters.CATEGORY_ID.toLowerCase()));
		Boolean returnUnassigned = Boolean.valueOf(req.getParameter(Parameters.RETURN_UNASSIGNED_ATTRIBUTES));
		
		Locale locale = LocaleUtils.toLocale(CookieManager.getValue(req, Attributes.LOCALE));
		
		Map<String, AttributeDTO<?>> attributes;
		
		try {
			attributes = service.findByCategory(categoryId, locale, returnUnassigned);
		} catch (Exception e) {
			logger.error("Failed to fetch attributes for category ID {}.", categoryId);
			attributes = Collections.emptyMap();
		}
		
		Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(AttributeDTO.class, AttributeSerializer.getInstance()).create();
		String json = gson.toJson(attributes.values());
		
		logger.debug("Results of product attribute request: {}", json);

		resp.getWriter().append(json);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
