package com.pinguela.yourpc.web.controller;

import java.io.IOException;

import com.pinguela.YPCException;
import com.pinguela.yourpc.model.dto.LocalizedProductDTO;
import com.pinguela.yourpc.service.ProductService;
import com.pinguela.yourpc.service.impl.ProductServiceImpl;
import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.constants.Views;
import com.pinguela.yourpc.web.util.LocaleUtils;
import com.pinguela.yourpc.web.util.RouterUtils;
import com.pinguela.yourpc.web.util.ValidatorUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProductServlet
 */
@SuppressWarnings("serial")
public class ProductServlet extends HttpServlet {
	
	private ProductService service;
       
    public ProductServlet() {
        this.service = new ProductServiceImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Long productId = ValidatorUtils.parseLong(request, Parameters.PRODUCT_ID, request.getParameter(Parameters.PRODUCT_ID));
		
		LocalizedProductDTO p;
		
		try {
			 p = service.findByIdLocalized(productId, LocaleUtils.getLocale(request));
		} catch (YPCException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		} 
		
		request.setAttribute(Attributes.PRODUCT, p);
		RouterUtils.setTargetView(request, Views.PRODUCT_DETAILS_VIEW);
		RouterUtils.route(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
