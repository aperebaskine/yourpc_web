package com.pinguela.yourpc.web.controller.processor;

import static com.pinguela.yourpc.web.constants.Parameters.NAME;

import java.io.IOException;

import com.pinguela.YPCException;
import com.pinguela.yourpc.model.ProductCriteria;
import com.pinguela.yourpc.model.Results;
import com.pinguela.yourpc.model.dto.ProductDTO;
import com.pinguela.yourpc.service.ProductService;
import com.pinguela.yourpc.service.impl.ProductServiceImpl;
import com.pinguela.yourpc.web.constants.Actions;
import com.pinguela.yourpc.web.controller.ProductServlet;
import com.pinguela.yourpc.web.model.Route;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ActionProcessor(action = Actions.RESULTS, servlets = ProductServlet.class)
public class ProductSearchActionProcessor 
extends AbstractActionProcessor {
	
	private ProductService service;
		
	ProductSearchActionProcessor() {
		service = new ProductServiceImpl();
	}

	@Override
	public void processAction(HttpServletRequest request, HttpServletResponse response, Route route)
			throws ServletException, IOException, YPCException {
		ProductCriteria criteria = new ProductCriteria();
		criteria.setName(request.getParameter(NAME));

		Results<ProductDTO> results = service.findBy(criteria, 1, 10);
		request.setAttribute("results", results);
		route.setTargetView("/product/product-results-view.jsp");
	}

}
