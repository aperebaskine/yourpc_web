package com.pinguela.yourpc.web.controller.processor;

import java.io.IOException;

import com.pinguela.YPCException;
import com.pinguela.yourpc.model.ProductCriteria;
import com.pinguela.yourpc.model.Results;
import com.pinguela.yourpc.model.dto.LocalizedProductDTO;
import com.pinguela.yourpc.service.ProductService;
import com.pinguela.yourpc.service.impl.ProductServiceImpl;
import com.pinguela.yourpc.web.annotations.ActionProcessor;
import com.pinguela.yourpc.web.constants.Actions;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.constants.Views;
import com.pinguela.yourpc.web.model.ErrorReport;
import com.pinguela.yourpc.web.util.LocaleUtils;
import com.pinguela.yourpc.web.util.RouterUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ActionProcessor(action = Actions.PRODUCT_RESULTS)
public class ProductResultsActionProcessor 
extends AbstractActionProcessor {
	
	private ProductService service;
		
	public ProductResultsActionProcessor() {
		service = new ProductServiceImpl();
	}

	@Override
	public void processAction(HttpServletRequest request, HttpServletResponse response, ErrorReport errors)
			throws ServletException, IOException, YPCException {
		ProductCriteria criteria = new ProductCriteria();
		criteria.setName(request.getParameter(Parameters.NAME));
		if (!request.getParameter(Parameters.CATEGORY_ID).isEmpty()) {
			criteria.setCategoryId(Short.valueOf(request.getParameter(Parameters.CATEGORY_ID)));
		}
		Results<LocalizedProductDTO> results = 
				service.findBy(criteria, LocaleUtils.getLocale(request), 1, 10);
		request.setAttribute("results", results);
		
		RouterUtils.setTargetView(request, Views.PRODUCT_RESULTS_VIEW);
	}

}
