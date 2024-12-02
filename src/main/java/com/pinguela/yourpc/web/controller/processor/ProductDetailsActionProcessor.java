package com.pinguela.yourpc.web.controller.processor;

import java.io.IOException;

import com.pinguela.YPCException;
import com.pinguela.yourpc.model.dto.LocalizedProductDTO;
import com.pinguela.yourpc.service.ProductService;
import com.pinguela.yourpc.service.impl.ProductServiceImpl;
import com.pinguela.yourpc.web.annotations.ActionProcessor;
import com.pinguela.yourpc.web.constants.Actions;
import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.constants.Views;
import com.pinguela.yourpc.web.exception.InputValidationException;
import com.pinguela.yourpc.web.model.ErrorReport;
import com.pinguela.yourpc.web.util.LocaleUtils;
import com.pinguela.yourpc.web.util.RouterUtils;
import com.pinguela.yourpc.web.util.ValidatorUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ActionProcessor(action = Actions.PRODUCT_DETAILS)
public class ProductDetailsActionProcessor extends AbstractActionProcessor {

	private ProductService service;
    
    public ProductDetailsActionProcessor() {
        this.service = new ProductServiceImpl();
    }
	
	@Override
	public void processAction(HttpServletRequest request, HttpServletResponse response, ErrorReport errors)
			throws ServletException, IOException, YPCException, InputValidationException {

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
	}

}