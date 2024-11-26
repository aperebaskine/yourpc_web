package com.pinguela.yourpc.web.controller.processor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.function.FailableConsumer;

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
import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.constants.Views;
import com.pinguela.yourpc.web.exception.InputValidationException;
import com.pinguela.yourpc.web.model.ErrorReport;
import com.pinguela.yourpc.web.util.LocaleUtils;
import com.pinguela.yourpc.web.util.PaginationUtils;
import com.pinguela.yourpc.web.util.RouterUtils;
import com.pinguela.yourpc.web.util.ValidatorUtils;

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

		ProductCriteria criteria = buildCriteria(request);
		Results<LocalizedProductDTO> results = fetchResults(request, criteria);
		request.setAttribute(Attributes.RESULTS, results);
		PaginationUtils.buildPaginationUrls(request);
		RouterUtils.setTargetView(request, Views.PRODUCT_RESULTS_VIEW);
	}

	private ProductCriteria buildCriteria(HttpServletRequest request) 
			throws InputValidationException, YPCException, IOException {

		ProductCriteria criteria = new ProductCriteria();
		Map<String, FailableConsumer<String, InputValidationException>> map = new HashMap<>();
		map.put(Parameters.NAME, value -> criteria.setName(value));

		new ParameterProcessor(request)
		.optional(Parameters.NAME, ValidatorUtils::hasContent, criteria::setName)
		.optional(Parameters.CATEGORY_ID, ValidatorUtils::parseShort, ValidatorUtils::isValidCategory, criteria::setCategoryId)
		.optional(Parameters.PRICE_FROM, ValidatorUtils::parseDouble, criteria::setPriceMin)
		.optional(Parameters.PRICE_TO, ValidatorUtils::parseDouble, criteria::setPriceMax)
		.optional(Parameters.STOCK_FROM, ValidatorUtils::parseInt, criteria::setStockMin)
		.optional(Parameters.STOCK_TO, ValidatorUtils::parseInt, criteria::setStockMax)
		.optional(Parameters.LAUNCH_DATE_FROM, ValidatorUtils::parseDate, criteria::setLaunchDateMin)
		.optional(Parameters.LAUNCH_DATE_TO, ValidatorUtils::parseDate, criteria::setLaunchDateMax);

		if (criteria.getCategoryId() != null) {
			criteria.setAttributes(buildAttributeCriteria(request, criteria.getCategoryId()));
		}
		
		return criteria;
	}

	private final List<AttributeDTO<?>> buildAttributeCriteria(HttpServletRequest request, Short categoryId) 
			throws InputValidationException, YPCException, IOException {

		List<AttributeDTO<?>> list = new ArrayList<AttributeDTO<?>>();

		Map<String, String[]> parameterMap = request.getParameterMap();
		Iterator<String> attributeKeyIterator = 
				parameterMap.keySet().stream().filter(t -> ATTRIBUTE_PARAMETER_REGEX.matcher(t).matches()).iterator();

		while (attributeKeyIterator.hasNext()) {
			String key = attributeKeyIterator.next();
			String[] keyComponents = key.split("\\.");
			String dataTypeIdentifier = keyComponents[1];

			if (!AttributeDataTypes.isValidType(dataTypeIdentifier)) {
				throw new InputValidationException(
						String.format("Attribute parameter %s contains unrecognized data type.", key));
			}

			AttributeDTO<?> dto = AttributeDTO.getInstance(dataTypeIdentifier);
			dto.setId(Integer.valueOf(keyComponents[2]));

			String[] parameters = parameterMap.get(key);
			for (String parameter : parameters) {
				try {
					dto.addValue(null, parameter);
				} catch (IllegalArgumentException e) {
					throw new InputValidationException(String.format(
							"Parameter %s does not match data type %s.", parameter, dataTypeIdentifier), e);
				}
			}

			if (AttributeValueHandlingModes.RANGE != dto.getValueHandlingMode()
					|| RANGE_VALIDATOR.validate(dto, categoryId)) {
				list.add(dto);
			}
		}

		return list;
	}

	private Results<LocalizedProductDTO> fetchResults(HttpServletRequest request, ProductCriteria criteria) 
			throws YPCException {

		String pageStr = request.getParameter(Parameters.PAGE);
		int pageSize = PaginationUtils.DEFAULT_PAGE_SIZE;
		int pos = ((pageStr == null ? 
				PaginationUtils.FIRST_PAGE_INDEX : 
					Integer.valueOf(pageStr))
				* pageSize) - pageSize + 1;

		return productService.findBy(criteria,
				LocaleUtils.getLocale(request), pos, pageSize);
	}

}
