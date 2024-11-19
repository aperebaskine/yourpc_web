package com.pinguela.yourpc.web.controller.processor;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.YPCException;
import com.pinguela.yourpc.model.ProductCriteria;
import com.pinguela.yourpc.model.Results;
import com.pinguela.yourpc.model.constants.AttributeValueHandlingModes;
import com.pinguela.yourpc.model.dto.AttributeDTO;
import com.pinguela.yourpc.model.dto.LocalizedProductDTO;
import com.pinguela.yourpc.service.AttributeService;
import com.pinguela.yourpc.service.ProductService;
import com.pinguela.yourpc.service.impl.AttributeServiceImpl;
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

	private static Logger logger = LogManager.getLogger(ProductResultsActionProcessor.class);

	private ProductService productService;
	private AttributeService attributeService;

	public ProductResultsActionProcessor() {
		productService = new ProductServiceImpl();
		attributeService = new AttributeServiceImpl();
	}

	@Override
	public void processAction(HttpServletRequest request, HttpServletResponse response, ErrorReport errors)
			throws ServletException, IOException, YPCException {
		ProductCriteria criteria = new ProductCriteria();
		criteria.setName(request.getParameter(Parameters.NAME));
		if (!request.getParameter(Parameters.CATEGORY_ID).isEmpty()) {
			criteria.setCategoryId(Short.valueOf(request.getParameter(Parameters.CATEGORY_ID)));
		}

		Map<String, String[]> attributeMap = request.getParameterMap();
		Iterator<String> attributeKeyIterator = 
				attributeMap.keySet().stream().filter(t -> t.contains("attr")).iterator();

		while (attributeKeyIterator.hasNext()) {
			String key = attributeKeyIterator.next();
			String[] parameters = attributeMap.get(key);

			for (String parameter : parameters) {
				Integer attributeId = getAttributeId(parameter);
				String dataTypeIdentifier = getDataTypeIdentifier(request, parameter);
				
				AttributeDTO<?> dto;
				
				try {
					dto = AttributeDTO.getInstance(dataTypeIdentifier);
				} catch (IllegalArgumentException e) {
					logger.warn("Incorrect data type provided: {}", dataTypeIdentifier);
					response.sendError(HttpServletResponse.SC_BAD_REQUEST);
					return;
				}
				
				dto.setId(attributeId);

				switch (dto.getValueHandlingMode()) {
				case AttributeValueHandlingModes.RANGE:
					break;
				case AttributeValueHandlingModes.SET:
					break;
				}
			}

			logger.info(key + "=" +request.getParameter(key));
		}

		Results<LocalizedProductDTO> results = 
				productService.findBy(criteria, LocaleUtils.getLocale(request), 1, 10);
		request.setAttribute("results", results);

		RouterUtils.setTargetView(request, Views.PRODUCT_RESULTS_VIEW);
	}
	
	private static final Integer getAttributeId(String attributeParameter) {
		return Integer.valueOf(attributeParameter.replace("attr", ""));
	}

	private static final String getDataTypeIdentifier(HttpServletRequest request, String attributeParameter) {
		return request.getParameter(attributeParameter.replace("attr", "dt"));
	}
	
}
