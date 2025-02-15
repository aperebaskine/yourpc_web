package com.pinguela.yourpc.web.util;

import java.util.List;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.model.CustomerCriteria;
import com.pinguela.yourpc.service.CustomerService;
import com.pinguela.yourpc.service.impl.CustomerServiceImpl;
import com.pinguela.yourpc.util.CategoryUtils;
import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.constants.ErrorCodes;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.model.ErrorReport;

/**
 * TODO: Separate validation and error logging (?), parse without catching exceptions
 */
public class ValidatorUtils {

	private static Logger logger = LogManager.getLogger(ValidatorUtils.class);
	private static CustomerService customerService = new CustomerServiceImpl();

	private static final String[] EMPTY_ARRAY = {};

	private static final int PASSWORD_MIN_LENGTH = 8;
	private static final int PASSWORD_MAX_LENGTH = 20;

	private static final Pattern SPECIAL_CHARACTER_REGEX = Pattern.compile("[^\\w]");
	
	public static boolean isBlank(String string) {
		return GenericValidator.isBlankOrNull(string);
	}

	public static String getParameter(HttpServletRequest request, String parameterName, boolean isRequired) {
		String parameterValue = request.getParameter(parameterName);

		if (parameterValue != null) {
			parameterValue = parameterValue.trim();
		}
		if (!hasContent(request, parameterValue)) {
			if (isRequired) {
				logFieldError(request, parameterName, ErrorCodes.MISSING_PARAMETER_VALUE);
			}
			return null;
		}
		return parameterValue;
	}

	public static String[] getParameterValues(HttpServletRequest request, String parameterName, Integer minCount, Integer maxCount) {
		String[] parameterValues = request.getParameterValues(parameterName);

		if (!isInRange(maxCount, minCount, maxCount)) {
			logFieldError(request, parameterName, parameterName);
			return EMPTY_ARRAY;
		}
		return parameterValues;
	}

	public static boolean hasContent(HttpServletRequest request, String string) {
		return !GenericValidator.isBlankOrNull(string);
	}

	public static boolean isValidCategory(HttpServletRequest request, String parameterName, Short categoryId) {
		if (!CategoryUtils.CATEGORIES.keySet().contains(categoryId)) {
			logFieldError(request, parameterName, ErrorCodes.NON_EXISTENT_CATEGORY);
			return false;
		} else {
			return true;
		}
	}

	public static boolean isValidUsername(HttpServletRequest request, String parameterName, String username) {
		boolean isInRange = isInRange(username.length(), PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

		if (!isInRange) {
			logFieldError(request, parameterName, ErrorCodes.INVALID_LENGTH);
			return false;
		}

		if (SPECIAL_CHARACTER_REGEX.matcher(username).matches()) {
			logFieldError(request, parameterName, ErrorCodes.INVALID_FORMAT);
			return false;
		}

		return true;
	}

	public static boolean isValidPassword(HttpServletRequest request, String parameterName, String password) {
		boolean isInRange = isInRange(password.length(), PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

		if (!isInRange) {
			logFieldError(request, parameterName, ErrorCodes.INVALID_LENGTH);
			return false;
		}

		boolean containsLowercase = false;
		boolean containsUppercase = false;
		boolean containsSpecial = false;

		for (int i = 0; i < password.length() && !(containsLowercase && containsUppercase); i++) {
			char c = password.charAt(i);

			if (!containsLowercase) {
				containsLowercase = Character.isLowerCase(c);
			}

			if (!containsUppercase) {
				containsUppercase = Character.isUpperCase(c);
			}
		}

		containsSpecial = SPECIAL_CHARACTER_REGEX.matcher(password).find(0);

		if (!(isInRange && containsLowercase && containsUppercase && containsSpecial)) {
			logFieldError(request, parameterName, ErrorCodes.INVALID_FORMAT);
			return false;
		}

		String repeatPassword = request.getParameter(Parameters.REPEAT_PASSWORD);
		if (!password.equals(repeatPassword)) {
			logFieldError(request, Parameters.REPEAT_PASSWORD, ErrorCodes.PASSWORDS_DO_NOT_MATCH);
		}

		return true;
	}

	public static boolean isValidPhoneNumber(HttpServletRequest request, String parameterName, String phoneNumber) {
		CustomerCriteria criteria = new CustomerCriteria();
		criteria.setPhoneNumber(phoneNumber);

		List<Customer> customers;
		try {
			customers = customerService.findBy(criteria);
		} catch (ServiceException | DataException e) {
			logger.error(e);
			logGlobalError(request, ErrorCodes.UNKNOWN_ERROR);
			return false;
		}

		if (!customers.isEmpty()) {
			Customer inSession = (Customer) SessionManager.getAttribute(request, Attributes.CUSTOMER);

			if (inSession == null || !inSession.getId().equals(customers.get(0).getId())) {
				logFieldError(request, parameterName, ErrorCodes.PHONE_NUMBER_IN_USE);
				return false;
			}
		}

		return true;
	}

	public static boolean isValidEmail(HttpServletRequest request, String parameterName, String email) {
		if (!GenericValidator.isEmail(email)) {
			logFieldError(request, parameterName, ErrorCodes.INVALID_FORMAT);
			return false;
		}

		CustomerCriteria criteria = new CustomerCriteria();
		criteria.setEmail(email);

		List<Customer> customers;

		try {
			customers = customerService.findBy(criteria);
		} catch (ServiceException | DataException e) {
			logger.error(e);
			logGlobalError(request, ErrorCodes.UNKNOWN_ERROR);
			return false;
		}

		if (!customers.isEmpty()) {
			Customer inSession = (Customer) SessionManager.getAttribute(request, Attributes.CUSTOMER);

			if (inSession == null || !inSession.getId().equals(customers.get(0).getId())) {
				logFieldError(request, parameterName, ErrorCodes.EMAIL_IN_USE);
				return false;
			}
		}


		return true;
	}

	public static boolean isInRange(Integer num, Integer min, Integer max) {
		return (min == null || num >= min) && (max == null || num <= max);
	}

	public static void logFieldError(HttpServletRequest request, String parameterName, String errorCode) {
		if (parameterName != null) {
			ErrorReport errors = (ErrorReport) request.getAttribute(Attributes.ERRORS);
			errors.addFieldError(parameterName, errorCode);
		}
	}

	public static void logGlobalError(HttpServletRequest request, String errorCode) {
		ErrorReport errors = (ErrorReport) request.getAttribute("errors");
		errors.addGlobalError(errorCode);
	}

	public static <T> BiFunction<HttpServletRequest, String, String> nopParser() {
		return (req, value) -> value;
	}

}
