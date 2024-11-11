package com.pinguela.yourpc.web.controller.processor;

import static com.pinguela.yourpc.web.constants.Parameters.NAME;

import java.io.IOException;

import com.pinguela.YPCException;
import com.pinguela.yourpc.model.ProductCriteria;
import com.pinguela.yourpc.model.Results;
import com.pinguela.yourpc.model.dto.LocalizedProductDTO;
import com.pinguela.yourpc.service.ProductService;
import com.pinguela.yourpc.service.impl.ProductServiceImpl;
import com.pinguela.yourpc.web.annotations.ActionProcessor;
import com.pinguela.yourpc.web.constants.Actions;
import com.pinguela.yourpc.web.constants.Views;
import com.pinguela.yourpc.web.model.ErrorReport;
import com.pinguela.yourpc.web.util.RouterUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ActionProcessor(action = Actions.PRODUCT_SEARCH)
public class ProductSearchActionProcessor 
extends AbstractActionProcessor {
	
	private ProductService service;
		
	public ProductSearchActionProcessor() {
		service = new ProductServiceImpl();
	}

	@Override
	public void processAction(HttpServletRequest request, HttpServletResponse response, ErrorReport errors)
			throws ServletException, IOException, YPCException {
		ProductCriteria criteria = new ProductCriteria();
		criteria.setName(request.getParameter(NAME));

		Results<LocalizedProductDTO> results = service.findBy(criteria, null, 1, 10);
		request.setAttribute("results", results);
		
		RouterUtils.setTargetView(request, Views.PRODUCT_RESULTS_VIEW);
	}

}
