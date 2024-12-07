package com.pinguela.yourpc.web.controller.processor;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pinguela.YPCException;
import com.pinguela.yourpc.model.Province;
import com.pinguela.yourpc.service.ProvinceService;
import com.pinguela.yourpc.service.impl.ProvinceServiceImpl;
import com.pinguela.yourpc.web.annotations.ActionProcessor;
import com.pinguela.yourpc.web.constants.Actions;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.controller.UserServlet;
import com.pinguela.yourpc.web.exception.InputValidationException;
import com.pinguela.yourpc.web.model.ErrorReport;
import com.pinguela.yourpc.web.util.ValidatorUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ActionProcessor(action = Actions.FETCH_PROVINCES, servlets = UserServlet.class)
public class FetchProvincesActionProcessor
extends AbstractActionProcessor {
	
	private ProvinceService service;
	
	public FetchProvincesActionProcessor() {
		service = new ProvinceServiceImpl();
	}

	@Override
	public void processAction(HttpServletRequest request, HttpServletResponse response, ErrorReport errors)
			throws ServletException, IOException, YPCException, InputValidationException {

		String countryId = ValidatorUtils.getParameter(request, Parameters.COUNTRY, true);
		
		if (countryId == null) {
			return;
		}
		
		List<Province> provinces = service.findByCountry(countryId);
		
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(provinces);
		
		response.getWriter().append(json);
	}

}
