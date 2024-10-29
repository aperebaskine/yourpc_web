package com.pinguela.yourpc.web.controller.processor;

import static com.pinguela.yourpc.web.constants.Parameters.NAME;

import java.io.IOException;

import com.pinguela.YPCException;
import com.pinguela.yourpc.model.Product;
import com.pinguela.yourpc.model.ProductCriteria;
import com.pinguela.yourpc.model.Results;
import com.pinguela.yourpc.service.ProductService;
import com.pinguela.yourpc.web.constants.RouteMethod;
import com.pinguela.yourpc.web.controller.ProductServlet;
import com.pinguela.yourpc.web.model.Route;
import com.pinguela.yourpc.web.util.ServiceManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ProductSearchActionProcessor 
extends AbstractActionProcessor {
		
	public ProductSearchActionProcessor() {
		super((String) null, ProductServlet.class);
	}

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response, Route route)
			throws ServletException, IOException, YPCException {
		ProductCriteria criteria = new ProductCriteria();
		criteria.setName(request.getParameter(NAME));

		Results<Product> results = ServiceManager.get(ProductService.class).findBy(criteria, 1, 10);
		request.setAttribute("results", results);
		route.setTargetView("/product/product-results-view.jsp");
		route.setRouteMethod(RouteMethod.FORWARD);
	}

}
