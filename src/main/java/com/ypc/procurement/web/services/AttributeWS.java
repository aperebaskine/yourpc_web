package com.ypc.procurement.web.services;

import java.io.IOException;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.controller.AttributeSerializer;
import com.pinguela.yourpc.web.util.LocaleUtils;

@SuppressWarnings("serial")
@WebServlet("/attributes")
public class AttributeWS extends HttpServlet {
	
	private static Logger logger = LogManager.getLogger(AttributeWS.class);
	
	private static final Gson GSON = new GsonBuilder()
			.registerTypeHierarchyAdapter(AttributeDTO.class, AttributeSerializer.getInstance())
			.create();
	
	private AttributeService attributeService;

    public AttributeWS() {
        super();
        this.attributeService = new AttributeServiceImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Short categoryId = Short.valueOf(request.getParameter(Parameters.CATEGORY_ID.toLowerCase()));
		Boolean returnUnassigned = Boolean.valueOf(request.getParameter(Parameters.RETURN_UNASSIGNED_ATTRIBUTES));
		
		Locale locale = LocaleUtils.getLocale(request);
		
		Map<String, AttributeDTO<?>> attributes;
		
		try {
			attributes = attributeService.findByCategory(categoryId, locale, returnUnassigned);
		} catch (Exception e) {
			logger.error("Failed to fetch attributes for category ID {}.", categoryId);
			attributes = Collections.emptyMap();
		}
		
		String json = GSON.toJson(attributes.values());
		
		logger.debug("Results of product attribute request: {}", json);

		response.setContentType("application/json");
		response.getOutputStream().write(json.getBytes());
		response.getOutputStream().flush();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
