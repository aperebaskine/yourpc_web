package com.pinguela.yourpc.web.controller.processor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.pinguela.YPCException;
import com.pinguela.yourpc.model.ProductCriteria;
import com.pinguela.yourpc.model.Results;
import com.pinguela.yourpc.model.constants.AttributeDataTypes;
import com.pinguela.yourpc.model.constants.AttributeValueHandlingModes;
import com.pinguela.yourpc.model.dto.AttributeDTO;
import com.pinguela.yourpc.model.dto.LocalizedProductDTO;
import com.pinguela.yourpc.service.ProductService;
import com.pinguela.yourpc.service.impl.ProductServiceImpl;
import com.pinguela.yourpc.util.validator.AttributeRangeValidator;
import com.pinguela.yourpc.web.annotations.ActionProcessor;
import com.pinguela.yourpc.web.constants.Actions;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.constants.Views;
import com.pinguela.yourpc.web.exception.InputValidationException;
import com.pinguela.yourpc.web.model.ErrorReport;
import com.pinguela.yourpc.web.util.LocaleUtils;
import com.pinguela.yourpc.web.util.PaginationUtils;
import com.pinguela.yourpc.web.util.RouterUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ActionProcessor(action = Actions.PRODUCT_RESULTS)
public class ProductResultsActionProcessor 
extends AbstractActionProcessor {

	private static final Pattern ATTRIBUTE_PARAMETER_REGEX = Pattern.compile("attr\\.[A-Z]{3}\\.[0-9]+");
	private static final AttributeRangeValidator RANGE_VALIDATOR = AttributeRangeValidator.getInstance();

	private ProductService productService;

	public ProductResultsActionProcessor() {
		productService = new ProductServiceImpl();
	}

	@Override
	public void processAction(HttpServletRequest request, HttpServletResponse response, ErrorReport errors)
			throws ServletException, IOException, YPCException, InputValidationException {
		ProductCriteria criteria = new ProductCriteria();
		criteria.setName(request.getParameter(Parameters.NAME));
		if (!request.getParameter(Parameters.CATEGORY_ID).isEmpty()) {
			criteria.setCategoryId(Short.valueOf(request.getParameter(Parameters.CATEGORY_ID)));
		}

		criteria.setAttributes(getAttributeCriteria(request, response));

		String pageStr = request.getParameter(Parameters.PAGE);
		int pageSize = PaginationUtils.DEFAULT_PAGE_SIZE;
		int pos = ((pageStr == null ? PaginationUtils.DEFAULT_PAGE : Integer.valueOf(pageStr)) * pageSize) - pageSize + 1;

		Results<LocalizedProductDTO> results = productService.findBy(criteria,
				LocaleUtils.getLocale(request), pos, pageSize);
		request.setAttribute("results", results);
		PaginationUtils.buildPaginationUrls(request);
		RouterUtils.setTargetView(request, Views.PRODUCT_RESULTS_VIEW);
	}

	private final List<AttributeDTO<?>> getAttributeCriteria(HttpServletRequest request, HttpServletResponse response) 
			throws IOException, YPCException, InputValidationException {

		List<AttributeDTO<?>> list = new ArrayList<AttributeDTO<?>>();

		Map<String, String[]> attributeMap = request.getParameterMap();
		Iterator<String> attributeKeyIterator = 
				attributeMap.keySet().stream().filter(t -> ATTRIBUTE_PARAMETER_REGEX.matcher(t).matches()).iterator();

		while (attributeKeyIterator.hasNext()) {
			String key = attributeKeyIterator.next();			
			String dataTypeIdentifier = getDataTypeIdentifier(key);

			if (!AttributeDataTypes.isValidType(dataTypeIdentifier)) {
				throw new InputValidationException(
						String.format("Attribute parameter %s contains unrecognized data type.", key));
			}

			AttributeDTO<?> dto = AttributeDTO.getInstance(dataTypeIdentifier);
			dto.setId(getAttributeId(key));

			String[] parameters = attributeMap.get(key);
			for (String parameter : parameters) {
				try {
					dto.addValue(null, parameter);
				} catch (IllegalArgumentException e) {
					throw new InputValidationException(String.format(
							"Parameter %s does not match data type %s.", parameter, dataTypeIdentifier), e);
				}
			}

			if (AttributeValueHandlingModes.RANGE != dto.getValueHandlingMode()
					|| RANGE_VALIDATOR.validate(dto, Short.valueOf(request.getParameter(Parameters.CATEGORY_ID)))) {
				list.add(dto);
			}
		}
		return list;
	}

	private static final Integer getAttributeId(String attributeParameter) {
		return Integer.valueOf(attributeParameter.split("\\.")[2]);
	}

	private static final String getDataTypeIdentifier(String attributeParameter) {
		return attributeParameter.split("\\.")[1];
	}

}
