package com.pinguela.yourpc.web.util;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.yourpc.web.constants.ErrorCodes;

public class ParameterParser {

	private static Logger logger = LogManager.getLogger(ParameterParser.class);

	private static final Map<Class<?>, BiFunction<HttpServletRequest, String, ?>> PARSERS = new ConcurrentHashMap<>();
	
	public static Short parseShort(HttpServletRequest request, String value) {
		return parse(request, null, value, Short::valueOf, false);
	}
	
	public static Integer parseInt(HttpServletRequest request, String value) {
		return parse(request, null, value, Integer::valueOf, false);
	}
	
	public static Long parseLong(HttpServletRequest request, String value) {
		return parse(request, null, value, Long::valueOf, false);
	}
	
	public static Double parseDouble(HttpServletRequest request, String value) {
		return parse(request, null, value, Double::valueOf, false);
	}
	
	public static Boolean parseBoolean(HttpServletRequest request, String value) {
		return parse(request, null, value, Boolean::valueOf, false);
	}
	
	public static Date parseDate(HttpServletRequest request, String value) {
		return parse(request, null, value, s -> {
			try {
				return DateFormat.getDateTimeInstance().parse(s);
			} catch (ParseException e) {
				throw new IllegalArgumentException(e);
			}
		}
		, false);
	}
	
	private static <T> T parse(HttpServletRequest request, String parameterName, String value, Function<String, T> parser, boolean isRequired) {

		if (GenericValidator.isBlankOrNull(value)) {			
			return null;
		}
		
		T parsed = null;

		try {
			parsed = parser.apply(value);
		} catch (Exception e) {
			ValidatorUtils.logFieldError(request, parameterName, ErrorCodes.INVALID_FORMAT);
		}

		return parsed;
	}

	public static <T> T parse(HttpServletRequest request, String parameterName, Class<T> targetClass) {
		return parse(request, parameterName, targetClass, false);
	}

	public static <T> T parse(HttpServletRequest request, String parameterName, Class<T> targetClass, boolean isRequired) {

		String value = ValidatorUtils.getParameter(request, parameterName, isRequired);

		if (value == null) {
			return null;
		}

		BiFunction<HttpServletRequest, String, T> parser = getParser(targetClass);

		T parsed = null;

		try {
			parsed = parser.apply(request, value);
		} catch (Exception e) {
			ValidatorUtils.logFieldError(request, parameterName, ErrorCodes.INVALID_FORMAT);
		}

		return parsed;
	}

	public static <T> T parse(HttpServletRequest request, String parameterName, Class<T> targetClass, String globalError) {

		T parsed = parse(request, parameterName, targetClass, true);

		if (parsed == null) {
			ValidatorUtils.logGlobalError(request, globalError);
		}

		return parsed;
	}

	public static <T> List<T> parseMultiple(HttpServletRequest request, String parameterName, Class<T> targetClass) {

		String[] values = ValidatorUtils.getParameterValues(request, parameterName, null, null);

		if (values == null) {
			return null;
		}

		BiFunction<HttpServletRequest, String, T> parser = getParser(targetClass);

		List<T> parsed = new ArrayList<>();

		for (String value : values) {
			try {
				parsed.add(parser.apply(request, value));
			} catch (Exception e) {
				ValidatorUtils.logFieldError(request, parameterName, ErrorCodes.INVALID_FORMAT);
			}
		}
		return parsed;
	}

	@SuppressWarnings("unchecked")
	private static <T> BiFunction<HttpServletRequest, String, T> getParser(Class<T> targetClass) {
		return (BiFunction<HttpServletRequest, String, T>) PARSERS.computeIfAbsent(targetClass, (key) -> {

			String methodName = String.format("parse%s", key.getSimpleName());

			try {
				Method method = ParameterParser.class.getMethod(methodName);

				if (BiFunction.class.isAssignableFrom(method.getReturnType())) {
					return (BiFunction<HttpServletRequest, String, ?>) method.invoke(null);
				}
			} catch (Exception e) {
				logger.error("Exception {} thrown while getting parser function for class {}.", e.getMessage(), key.getName(), e);
			}

			throw new IllegalArgumentException(String.format("No parser found for class %s.", targetClass.getName()));
		});
	}

	public static BiFunction<HttpServletRequest, String, Date> parseDate() {
		return (request, parameterValue) -> {
			try {
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd", LocaleUtils.getLocale(request));
				return format.parse(parameterValue);
			} catch (ParseException e) {
				ValidatorUtils.logFieldError(request, "date", ErrorCodes.NOT_A_DATE);
				return null;
			}
		};
	}

	public static BiFunction<HttpServletRequest, String, Short> parseShort() {
		return (request, parameterValue) -> {
			try {
				return Short.valueOf(parameterValue);
			} catch (NumberFormatException e) {
				ValidatorUtils.logFieldError(request, "short", ErrorCodes.NOT_A_NUMBER);
				return null;
			}
		};
	}

	public static BiFunction<HttpServletRequest, String, Integer> parseInt() {
		return (request, parameterValue) -> {
			try {
				return Integer.valueOf(parameterValue);
			} catch (NumberFormatException e) {
				ValidatorUtils.logFieldError(request, "integer", ErrorCodes.NOT_A_NUMBER);
				return null;
			}
		};
	}

	public static BiFunction<HttpServletRequest, String, Double> parseDouble() {
		return (request, parameterValue) -> {
			try {
				return Double.valueOf(parameterValue);
			} catch (NumberFormatException e) {
				ValidatorUtils.logFieldError(request, "double", ErrorCodes.NOT_A_NUMBER);
				return null;
			}
		};
	}

	public static BiFunction<HttpServletRequest, String, Boolean> parseBoolean() {
		return (request, parameterValue) -> Boolean.valueOf(parameterValue);
	}

}
