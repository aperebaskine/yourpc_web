package com.pinguela.yourpc.web.controller.processor;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.constants.Views;
import com.pinguela.yourpc.web.exception.InputValidationException;
import com.pinguela.yourpc.web.model.ErrorReport;
import com.pinguela.yourpc.web.util.LocaleUtils;
import com.pinguela.yourpc.web.util.PaginationUtils;
import com.pinguela.yourpc.web.util.ParameterUtils;
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

		ProductCriteria criteria = buildCriteria(request);
		Results<LocalizedProductDTO> results = fetchResults(request, criteria);
		request.setAttribute(Attributes.RESULTS, results);
		PaginationUtils.buildPaginationUrls(request);
		RouterUtils.setTargetView(request, Views.PRODUCT_RESULTS_VIEW);
	}

	private ProductCriteria buildCriteria(HttpServletRequest request) 
			throws InputValidationException, YPCException, IOException {
		
		ProductCriteria criteria = new ProductCriteria();

		ParameterUtils.runIfPresent(request, Map.of(
				Parameters.NAME, name -> criteria.setName(name),
				Parameters.CATEGORY_ID, categoryId -> criteria.setCategoryId(Short.valueOf(categoryId)),
				Parameters.PRICE_FROM, priceMin -> criteria.setPriceMin(Double.valueOf(priceMin)),
				Parameters.PRICE_TO, priceMax -> criteria.setPriceMax(Double.valueOf(priceMax)),
				Parameters.STOCK_FROM, stockMin -> criteria.setStockMin(Integer.valueOf(stockMin)),
				Parameters.STOCK_TO, stockMax -> criteria.setStockMax(Integer.valueOf(stockMax)),
				Parameters.LAUNCH_DATE_FROM, launchDateMin -> criteria.setLaunchDateMin(parseDate(request, launchDateMin)),
				Parameters.LAUNCH_DATE_TO, launchDateMax -> criteria.setLaunchDateMax(parseDate(request, launchDateMax))
				));

		criteria.setAttributes(buildAttributeCriteria(request));

		return criteria;
	}

	private final List<AttributeDTO<?>> buildAttributeCriteria(HttpServletRequest request) 
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
					|| RANGE_VALIDATOR.validate(dto, Short.valueOf(request.getParameter(Parameters.CATEGORY_ID)))) {
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

	private static Date parseDate(HttpServletRequest request, String dateStr) 
			throws InputValidationException {
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd", LocaleUtils.getLocale(request));
			return format.parse(dateStr);
		} catch (ParseException e) {
			throw new InputValidationException(
					String.format("Cannot convert string %s to date.", dateStr), e);
		}
	}

}
