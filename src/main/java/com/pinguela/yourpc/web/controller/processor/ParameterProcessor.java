package com.pinguela.yourpc.web.controller.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

import com.pinguela.yourpc.web.constants.DiscardStrategy;
import com.pinguela.yourpc.web.util.ValidatorUtils;

import jakarta.servlet.http.HttpServletRequest;

public class ParameterProcessor {

	private HttpServletRequest request;

	public ParameterProcessor(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	public ParameterProcessor optional(String parameter,  
			BiPredicate<HttpServletRequest, String> validator, Consumer<String> consumer) {
		return processSingleValue(parameter, false, ValidatorUtils.nopParser(), validator, consumer);
	}

	public <T> ParameterProcessor optional(String parameter, BiFunction<HttpServletRequest, String, T> parser, 
			BiPredicate<HttpServletRequest, T> validator, Consumer<T> consumer) {
		return processSingleValue(parameter, false, parser, validator, consumer);
	}
	
	public ParameterProcessor required(String parameter, 
			BiPredicate<HttpServletRequest, String> validator, Consumer<String> consumer) {
		return processSingleValue(parameter, true, ValidatorUtils.nopParser(), validator, consumer);
	}
	
	public <T> ParameterProcessor required(String parameter, BiFunction<HttpServletRequest, String, T> parser, 
			BiPredicate<HttpServletRequest, T> validator, Consumer<T> consumer) {
		return processSingleValue(parameter, true, parser, validator, consumer);
	}
	
	public ParameterProcessor multiOptional(String parameter,
			BiPredicate<HttpServletRequest, String> validator, Consumer<String> consumer, DiscardStrategy discardStrategy) {
		return processMultipleValues(parameter, null, null, ValidatorUtils.nopParser(), validator, consumer, discardStrategy);
	}
	
	public <T> ParameterProcessor multiOptional(String parameter, BiFunction<HttpServletRequest, String, T> parser, 
			BiPredicate<HttpServletRequest, T> validator, Consumer<T> consumer, DiscardStrategy discardStrategy) {
		return processMultipleValues(parameter, null, null, parser, validator, consumer, discardStrategy);
	}
	
	public ParameterProcessor multiRequired(String parameter, int minValueCount, int maxValueCount, 
			BiPredicate<HttpServletRequest, String> validator, Consumer<String> consumer, DiscardStrategy discardStrategy) {
		return processMultipleValues(parameter, minValueCount, maxValueCount, ValidatorUtils.nopParser(), validator, consumer, discardStrategy);
	}
	
	public <T> ParameterProcessor multiRequired(String parameter, int minValueCount, int maxValueCount, 
			BiFunction<HttpServletRequest, String, T> parser, BiPredicate<HttpServletRequest, T> validator,
			Consumer<T> consumer, DiscardStrategy discardStrategy) {
		return processMultipleValues(parameter, minValueCount, maxValueCount, parser, validator, consumer, discardStrategy);
	}

	private <T> ParameterProcessor processSingleValue(String parameter, boolean isRequired, 
			BiFunction<HttpServletRequest, String, T> parser, BiPredicate<HttpServletRequest, T> validator, Consumer<T> consumer) {

		String parameterValue = ValidatorUtils.getParameter(request, parameter, isRequired);

		if (parameterValue != null) {
			T parsedValue = getParsedValue(parser, parameterValue);

			if (parsedValue != null && validateValue(validator, parsedValue)) {
				consumer.accept(parsedValue);
			}
		}

		return this;
	}

	private <T> ParameterProcessor processMultipleValues(String parameter, Integer minValueCount, Integer maxValueCount, 
			BiFunction<HttpServletRequest, String, T> parser, BiPredicate<HttpServletRequest, T> validator,
			Consumer<T> consumer, DiscardStrategy discardStrategy) {

		String[] parameterValues = ValidatorUtils.getParameterValues(request, parameter, minValueCount, maxValueCount);

		if (parameterValues.length == 0) {
			List<T> parsedValues = getParsedValues(parser, parameterValues, discardStrategy);

			if (validateValues(validator, parsedValues, discardStrategy)) {
				for (T parsedValue : parsedValues) {
					consumer.accept(parsedValue);
				}
			}
		}

		return this;
	}

	private <T> T getParsedValue(BiFunction<HttpServletRequest, String, T> parser, String value) {
		return parser.apply(request, value);
	}

	private <T> List<T> getParsedValues(BiFunction<HttpServletRequest, String, T> parser, String[] values,
			DiscardStrategy discardStrategy) {
		List<T> parsedValues = new ArrayList<T>();

		for (String value : values) {
			T parsedValue = getParsedValue(parser, value);
			if (parsedValue != null) {
				parsedValues.add(parsedValue);
			} else if (discardStrategy == DiscardStrategy.ALL) {
				return Collections.emptyList();
			}
		}

		return parsedValues;
	}

	private <T> boolean validateValue(BiPredicate<HttpServletRequest, T> validator, T value) {
		if (validator == null) {
			return true;
		}
		return validator.test(request, value);
	}

	private <T> boolean validateValues(BiPredicate<HttpServletRequest, T> validator, 
			List<T> values, DiscardStrategy discardStrategy) {
		if (validator == null) {
			return true;
		}

		for (T value : values) {
			if (validator.test(request, value)) {
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
