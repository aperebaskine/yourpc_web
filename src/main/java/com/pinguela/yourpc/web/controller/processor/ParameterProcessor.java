package com.pinguela.yourpc.web.controller.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.lang3.function.TriFunction;

import com.pinguela.yourpc.web.constants.DiscardStrategy;
import com.pinguela.yourpc.web.functions.TriPredicate;
import com.pinguela.yourpc.web.util.ValidatorUtils;

import jakarta.servlet.http.HttpServletRequest;

public class ParameterProcessor {

	private HttpServletRequest request;

	public ParameterProcessor(HttpServletRequest request) {
		super();
		this.request = request;
	}

	public ParameterProcessor optional(String parameter,  
			TriPredicate<HttpServletRequest, String, String> validator, Consumer<String> consumer) {
		return processSingleValue(parameter, false, ValidatorUtils.nopParser(), validator, consumer);
	}

	public <T> ParameterProcessor optional(String parameter, TriFunction<HttpServletRequest, String, String, T> parser, 
			TriPredicate<HttpServletRequest, String, T> validator, Consumer<T> consumer) {
		return processSingleValue(parameter, false, parser, validator, consumer);
	}

	public ParameterProcessor required(String parameter, 
			TriPredicate<HttpServletRequest, String, String> validator, Consumer<String> consumer) {
		return processSingleValue(parameter, true, ValidatorUtils.nopParser(), validator, consumer);
	}

	public <T> ParameterProcessor required(String parameter, TriFunction<HttpServletRequest, String, String, T> parser, 
			TriPredicate<HttpServletRequest, String, T> validator, Consumer<T> consumer) {
		return processSingleValue(parameter, true, parser, validator, consumer);
	}

	public ParameterProcessor multiOptional(String parameter,
			TriPredicate<HttpServletRequest, String, String> validator, Consumer<String> consumer, DiscardStrategy discardStrategy) {
		return processMultipleValues(parameter, null, null, ValidatorUtils.nopParser(), validator, consumer, discardStrategy);
	}

	public <T> ParameterProcessor multiOptional(String parameter, TriFunction<HttpServletRequest, String, String, T> parser, 
			TriPredicate<HttpServletRequest, String, T> validator, Consumer<T> consumer, DiscardStrategy discardStrategy) {
		return processMultipleValues(parameter, null, null, parser, validator, consumer, discardStrategy);
	}

	public ParameterProcessor multiRequired(String parameter, int minValueCount, int maxValueCount, 
			TriPredicate<HttpServletRequest, String, String> validator, Consumer<String> consumer, DiscardStrategy discardStrategy) {
		return processMultipleValues(parameter, minValueCount, maxValueCount, ValidatorUtils.nopParser(), validator, consumer, discardStrategy);
	}

	public <T> ParameterProcessor multiRequired(String parameter, int minValueCount, int maxValueCount, 
			TriFunction<HttpServletRequest, String, String, T> parser, TriPredicate<HttpServletRequest, String, T> validator,
			Consumer<T> consumer, DiscardStrategy discardStrategy) {
		return processMultipleValues(parameter, minValueCount, maxValueCount, parser, validator, consumer, discardStrategy);
	}

	private <T> ParameterProcessor processSingleValue(String parameter, boolean isRequired, 
			TriFunction<HttpServletRequest, String, String, T> parser, TriPredicate<HttpServletRequest, String, T> validator, Consumer<T> consumer) {

		String parameterValue = ValidatorUtils.getParameter(request, parameter, isRequired);

		if (parameterValue != null) {
			T parsedValue = getParsedValue(parser, parameter, parameterValue);

			if (parsedValue != null && validateValue(validator, parameter, parsedValue)) {
				consumer.accept(parsedValue);
			}
		}

		return this;
	}

	private <T> ParameterProcessor processMultipleValues(String parameter, Integer minValueCount, Integer maxValueCount, 
			TriFunction<HttpServletRequest, String, String, T> parser, TriPredicate<HttpServletRequest, String, T> validator,
			Consumer<T> consumer, DiscardStrategy discardStrategy) {

		String[] parameterValues = ValidatorUtils.getParameterValues(request, parameter, minValueCount, maxValueCount);

		if (parameterValues.length == 0) {
			List<T> parsedValues = getParsedValues(parser, parameter, parameterValues, discardStrategy);

			if (validateValues(validator, parameter, parsedValues, discardStrategy)) {
				for (T parsedValue : parsedValues) {
					consumer.accept(parsedValue);
				}
			}
		}

		return this;
	}

	private <T> T getParsedValue(TriFunction<HttpServletRequest, String, String, T> parser, String parameterName, String value) {
		return parser.apply(request, parameterName, value);
	}

	private <T> List<T> getParsedValues(TriFunction<HttpServletRequest, String, String, T> parser, String parameterName, String[] values,
			DiscardStrategy discardStrategy) {
		List<T> parsedValues = new ArrayList<T>();

		for (String value : values) {
			T parsedValue = getParsedValue(parser, parameterName, value);
			if (parsedValue != null) {
				parsedValues.add(parsedValue);
			} else if (discardStrategy == DiscardStrategy.ALL) {
				return Collections.emptyList();
			}
		}

		return parsedValues;
	}

	private <T> boolean validateValue(TriPredicate<HttpServletRequest, String, T> validator, String parameterName, T value) {
		if (validator == null) {
			return true;
		}
		return validator.test(request, parameterName, value);
	}

	private <T> boolean validateValues(TriPredicate<HttpServletRequest, String, T> validator, 
			String parameterName, List<T> values, DiscardStrategy discardStrategy) {
		if (validator == null) {
			return true;
		}

		for (T value : values) {
			if (validator.test(request, parameterName, value)) {
				switch (discardStrategy) {
				case INVALID_ONLY:
					values.remove(value);
					break;
				case ALL:
					return false;
				}
			}
		}

		return true;
	}

}
