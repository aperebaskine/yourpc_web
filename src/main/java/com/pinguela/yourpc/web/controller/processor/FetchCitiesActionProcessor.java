package com.pinguela.yourpc.web.controller.processor;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pinguela.YPCException;
import com.pinguela.yourpc.model.City;
import com.pinguela.yourpc.service.CityService;
import com.pinguela.yourpc.service.impl.CityServiceImpl;
import com.pinguela.yourpc.web.annotations.ActionProcessor;
import com.pinguela.yourpc.web.constants.Actions;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.controller.UserServlet;
import com.pinguela.yourpc.web.exception.InputValidationException;
import com.pinguela.yourpc.web.model.ErrorReport;
import com.pinguela.yourpc.web.util.ParameterParser;
import com.pinguela.yourpc.web.util.ValidatorUtils;

@ActionProcessor(action = Actions.FETCH_CITIES, servlets = UserServlet.class)
public class FetchCitiesActionProcessor
extends AbstractActionProcessor {
	
	private CityService service;
	
	public FetchCitiesActionProcessor() {
		service = new CityServiceImpl();
	}

	@Override
	public void processAction(HttpServletRequest request, HttpServletResponse response, ErrorReport errors)
			throws ServletException, IOException, YPCException, InputValidationException {

		String provinceIdStr = ValidatorUtils.getParameter(request, Parameters.PROVINCE, true);
		
		if (provinceIdStr == null) {
			return;
		}
		
		Integer provinceId = ParameterParser.parseInt(request, Parameters.PROVINCE);
		List<City> cities = service.findByProvince(provinceId);
		
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(cities);
		
		response.getWriter().append(json);
	}

}