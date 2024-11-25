package com.pinguela.yourpc.web.util;

import java.util.Map;

import org.apache.commons.lang3.function.FailableConsumer;

import com.pinguela.yourpc.web.exception.InputValidationException;

import jakarta.servlet.http.HttpServletRequest;

public class ParameterUtils {

	/**
	 * Validates the presence of a parameter in a request. If found, executes the provided function
	 * passing the parameter value as its single argument.
	 * @param request The request object
	 * @param parameter The name of the parameter
	 * @param consumer The function to execute if a parameter value is found 
	 * @throws InputValidationException If an {@link IllegalArgumentException} is thrown by the function,
	 * for example, if parsing the string fails.
	 */
	public static void runIfPresent(HttpServletRequest request, String parameter,
			FailableConsumer<String, InputValidationException> consumer) throws InputValidationException {

		String parameterValue = request.getParameter(parameter);

		if (parameterValue != null && !parameterValue.isBlank()) {
			try {
				consumer.accept(parameterValue);
			} catch (IllegalArgumentException e) {
				throw new InputValidationException(String.format(
						"Invalid value '%s' for parameter '%s'.", parameter, parameterValue));
			}
		} 
	}

	/**
	 * Validates the presence of multiple parameters in a request. If found, executes the function
	 * associated with the parameter, passing the parameter value as its single argument.
	 * @param request The request object
	 * @param functionMap Map associating parameters with functions to execute
	 * @throws InputValidationException If an {@link IllegalArgumentException} is thrown by any function,
	 * for example, if parsing the string fails.
	 */
	public static void runIfPresent(HttpServletRequest request, 
			Map<String, FailableConsumer<String, InputValidationException>> functionMap)
					throws InputValidationException {
		for (String parameter : functionMap.keySet()) {
			runIfPresent(request, parameter, functionMap.get(parameter));
		}
	}

	/**
	 * Validates the presence of a parameter in a request. If found, executes the provided function
	 * for each value corresponding to the parameter, with a value as its single argument.
	 * @param request The request object
	 * @param parameter The name of the parameter
	 * @param consumer The function to execute if a parameter value is found 
	 * @throws InputValidationException If an {@link IllegalArgumentException} is thrown by the function,
	 * for example, if parsing the string fails.
	 */
	public static void runIfPresentForEach(HttpServletRequest request, String parameter,
			FailableConsumer<String, InputValidationException> consumer) throws InputValidationException {

		String[] parameterValues = request.getParameterValues(parameter);

		if (parameterValues != null && parameterValues.length > 0) {
			try {
				for (String value : parameterValues) {
					consumer.accept(value);
				}
			} catch (IllegalArgumentException e) {
				throw new InputValidationException(String.format(
						"Invalid value '%s' for parameter '%s'.", parameter, parameterValues));
			}
		} 
	}

	/**
	 * Validates the presence of multiple parameters in a request. If found, executes the function
	 * for each value associated with the parameter, passing the parameter value as its single argument.
	 * @param request The request object
	 * @param parameter The name of the parameter
	 * @param consumer The function to execute if a parameter value is found 
	 * @throws InputValidationException If an {@link IllegalArgumentException} is thrown by the function,
	 * for example, if parsing the string fails.
	 */
	public static void runIfPresentForEach(HttpServletRequest request,
			Map<String, FailableConsumer<String, InputValidationException>> functionMap)
					throws InputValidationException {
		for (String parameter : functionMap.keySet()) {
			runIfPresentForEach(request, parameter, functionMap.get(parameter));
		}
	}

}
