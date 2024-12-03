package com.pinguela.yourpc.web.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang3.function.TriFunction;
import org.apache.commons.validator.GenericValidator;

import com.pinguela.yourpc.util.CategoryUtils;
import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.constants.ErrorCodes;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.model.ErrorReport;

import jakarta.servlet.http.HttpServletRequest;

public class ValidatorUtils {

	private static final String[] EMPTY_ARRAY = {};

	private static final int PASSWORD_MIN_LENGTH = 8;
	private static final int PASSWORD_MAX_LENGTH = 20;

	private static final Pattern SPECIAL_CHARACTER_REGEX = Pattern.compile("[^\\w\\d]");
	public static final Pattern EMAIL_REGEX = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*"
			+ "|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")"
			+ "@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}"
			+ "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");

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

		for (int i = 0; i < password.length() && !containsLowercase && !containsUppercase; i++) {
			char c = password.charAt(i);

			if (!containsLowercase) {
				containsLowercase = Character.isLowerCase(c);
			}

			if (!containsUppercase) {
				containsUppercase = Character.isUpperCase(c);
			}
		}

		containsSpecial = SPECIAL_CHARACTER_REGEX.matcher(password).matches();

		if (!(isInRange && containsLowercase && containsUppercase && containsSpecial)) {
			logFieldError(request, parameterName, ErrorCodes.INVALID_FORMAT);
			return false;
		}

		String repeatPassword = request.getParameter(Parameters.REPEAT_PASSWORD);
		if (!password.equals(repeatPassword)) {
			logGlobalError(request, ErrorCodes.PASSWORDS_DO_NOT_MATCH);
		}

		return true;
	}
	
	public static boolean isValidEmail(HttpServletRequest request, String parameterName, String email) {
		if (!EMAIL_REGEX.matcher(email).matches()) {
			logFieldError(request, parameterName, ErrorCodes.INVALID_FORMAT);
			return false;
		}
		return true;
	}

	public static boolean isInRange(Integer num, Integer min, Integer max) {
		return (min == null || num >= min) && (max == null || num <= max);
	}



	public static Date parseDate(HttpServletRequest request, String parameterName, String parameterValue) {
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd", LocaleUtils.getLocale(request));
			return format.parse(parameterValue);
		} catch (ParseException e) {
			logFieldError(request, parameterName, ErrorCodes.NOT_A_DATE);
			return null;
		}
	}

	public static Short parseShort(HttpServletRequest request, String parameterName, String parameterValue) {
		try {
			return Short.valueOf(parameterValue);
		} catch (NumberFormatException e) {
			logFieldError(request, parameterName, ErrorCodes.NOT_A_NUMBER);
			return null;
		}
	}

	public static Integer parseInt(HttpServletRequest request, String parameterName, String parameterValue) {
		try {
			return Integer.valueOf(parameterName);
		} catch (NumberFormatException e) {
			logFieldError(request, parameterName, ErrorCodes.NOT_A_NUMBER);
			return null;
		}
	}

	public static Double parseDouble(HttpServletRequest request, String parameterName, String parameterValue) {
		try {
			return Double.valueOf(parameterValue);
		} catch (NumberFormatException e) {
			logFieldError(request, parameterName, ErrorCodes.NOT_A_NUMBER);
			return null;
		}
	}

	public static Long parseLong(HttpServletRequest request, String parameterName, String parameterValue) {
		try {
			return Long.valueOf(parameterValue);
		} catch (NumberFormatException e) {
			logFieldError(request, parameterName, ErrorCodes.NOT_A_NUMBER);
			return null;
		}
	}

	public static void logFieldError(HttpServletRequest request, String parameterName, String errorCode) {
		ErrorReport errors = (ErrorReport) request.getAttribute(Attributes.ERRORS);
		errors.addFieldError(parameterName, errorCode);
	}

	public static void logGlobalError(HttpServletRequest request, String errorCode) {
		ErrorReport errors = (ErrorReport) request.getAttribute("errors");
		errors.addGlobalError(errorCode);
	}

	public static <T> TriFunction<HttpServletRequest, String, String, String> nopParser() {
		return (r, p, v) -> v;
	}

}
