package com.pinguela.yourpc.web.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.BiFunction;

import org.apache.commons.validator.GenericValidator;

import com.pinguela.yourpc.util.CategoryUtils;
import com.pinguela.yourpc.web.model.ErrorReport;

import jakarta.servlet.http.HttpServletRequest;

public class ValidatorUtils {
	
	public static final String[] EMPTY_ARRAY = {};
	
	public static String getParameter(HttpServletRequest request, String parameterName, boolean isRequired) {
		String parameterValue = request.getParameter(parameterName);
		if (!hasContent(request, parameterValue)) {
			if (isRequired) {
				logFieldError(request, parameterName, parameterValue);
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
	
	public static boolean isValidCategory(HttpServletRequest request, Short categoryId) {
		return CategoryUtils.CATEGORIES.keySet().contains(categoryId);
	}
	
	public static boolean isInRange(Integer num, Integer min, Integer max) {
		return (min == null || num >= min) && (max == null || num <= max);
	}
	
	public static Date parseDate(HttpServletRequest request, String dateStr) {
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd", LocaleUtils.getLocale(request));
			return format.parse(dateStr);
		} catch (ParseException e) {
			logFieldError(request, dateStr, dateStr);
			return null;
		}
	}
	
	public static Short parseShort(HttpServletRequest request, String shortStr) {
		try {
			return Short.valueOf(shortStr);
		} catch (NumberFormatException e) {
			ErrorReport errors = (ErrorReport) request.getAttribute("errors");
			errors.addFieldError(shortStr, shortStr);
			return null;
		}
	}
	
	public static Integer parseInt(HttpServletRequest request, String shortStr) {
		try {
			return Integer.valueOf(shortStr);
		} catch (NumberFormatException e) {
			ErrorReport errors = (ErrorReport) request.getAttribute("errors");
			errors.addFieldError(shortStr, shortStr);
			return null;
		}
	}
	
	public static Double parseDouble(HttpServletRequest request, String shortStr) {
		try {
			return Double.valueOf(shortStr);
		} catch (NumberFormatException e) {
			ErrorReport errors = (ErrorReport) request.getAttribute("errors");
			errors.addFieldError(shortStr, shortStr);
			return null;
		}
	}
	
	public static Long parseLong(HttpServletRequest request, String shortStr) {
		try {
			return Long.valueOf(shortStr);
		} catch (NumberFormatException e) {
			ErrorReport errors = (ErrorReport) request.getAttribute("errors");
			errors.addFieldError(shortStr, shortStr);
			return null;
		}
	}
	
	private static void logFieldError(HttpServletRequest request, String parameterName, String errorCode) {
		ErrorReport errors = (ErrorReport) request.getAttribute("errors");
		errors.addFieldError(parameterName, errorCode);
	}
	
	private static void logGlobalError(HttpServletRequest request, String errorCode) {
		ErrorReport errors = (ErrorReport) request.getAttribute("errors");
		errors.addGlobalError(errorCode);
	}
	
	public static BiFunction<HttpServletRequest, String, String> nopParser() {
		return (r, s) -> s;
	}

}
