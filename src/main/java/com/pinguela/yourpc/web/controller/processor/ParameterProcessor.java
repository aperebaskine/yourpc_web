package com.pinguela.yourpc.web.controller.processor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

import com.pinguela.yourpc.web.util.ValidatorUtils;

import jakarta.servlet.http.HttpServletRequest;

public class ParameterProcessor {

	private HttpServletRequest request;

	public ParameterProcessor(HttpServletRequest request) {
		super();
		this.request = request;
	}

	public ParameterProcessor optional(String parameter, Consumer<String> consumer) {
		return processSingleValue(parameter, false, null, Collections.emptyList(), consumer);
	}

	public ParameterProcessor optional(String parameter,
			BiPredicate<HttpServletRequest, String> validator, Consumer<String> consumer) {
		return processSingleValue(parameter, false, null, Arrays.asList(validator), consumer);
	}

	public ParameterProcessor optional(String parameter, 
			List<BiPredicate<HttpServletRequest, String>> validators, Consumer<String> consumer) {
		return processSingleValue(parameter, false, null, validators, consumer);
	}

	public <T> ParameterProcessor optional(String parameter, 
			BiFunction<HttpServletRequest, String, T> parser, Consumer<T> consumer) {
		return processSingleValue(parameter, false, parser, Collections.emptyList(), consumer);
	}

	public <T> ParameterProcessor optional(String parameter, BiFunction<HttpServletRequest, String, T> parser, 
			BiPredicate<HttpServletRequest, T> validator, Consumer<T> consumer) {
		return processSingleValue(parameter, false, parser, Arrays.asList(validator), consumer);
	}

	public <T> ParameterProcessor optional(String parameter, BiFunction<HttpServletRequest, String, T> parser, 
			List<BiPredicate<HttpServletRequest, T>> validators, Consumer<T> consumer) {
		return processSingleValue(parameter, false, parser, validators, consumer);
	}

	public ParameterProcessor required(String parameter, Consumer<String> consumer) {
		return processSingleValue(parameter, true, null, Collections.emptyList(), consumer);
	}

	public ParameterProcessor required(String parameter,
			BiPredicate<HttpServletRequest, String> validator, Consumer<String> consumer) {
		return processSingleValue(parameter, true, null, Arrays.asList(validator), consumer);
	}

	public ParameterProcessor required(String parameter, 
			List<BiPredicate<HttpServletRequest, String>> validators, Consumer<String> consumer) {
		return processSingleValue(parameter, true, null, validators, consumer);
	}

	public <T> ParameterProcessor required(String parameter, 
			BiFunction<HttpServletRequest, String, T> parser, Consumer<T> consumer) {
		return processSingleValue(parameter, true, parser, Collections.emptyList(), consumer);
	}

	public <T> ParameterProcessor required(String parameter, BiFunction<HttpServletRequest, String, T> parser, 
			BiPredicate<HttpServletRequest, T> validator, Consumer<T> consumer) {
		return processSingleValue(parameter, true, parser, Arrays.asList(validator), consumer);
	}

	public <T> ParameterProcessor required(String parameter, BiFunction<HttpServletRequest, String, T> parser, 
			List<BiPredicate<HttpServletRequest, T>> validators, Consumer<T> consumer) {
		return processSingleValue(parameter, true, parser, validators, consumer);
	}

	private <T> ParameterProcessor processSingleValue(String parameter, boolean isRequired, 
			BiFunction<HttpServletRequest, String, T> parser, List<BiPredicate<HttpServletRequest, T>> validators, Consumer<T> consumer) {

		String parameterValue = ValidatorUtils.getParameter(request, parameter, isRequired);

		if (parameterValue == null) {
			return this;
		}

		@SuppressWarnings("unchecked")
		T parsedValue = parser == null ? (T) parameterValue : parser.apply(request, parameterValue);

		if (parsedValue == null) {
			return this;
		}

		boolean validated = true;
		for (int i = 0; i < validators.size() && validated; i++) {
			validated = validated && validators.get(i).test(request, parsedValue);
		}

		if (validated) {
			consumer.accept(parsedValue);
		}

		return this;
	}
	
	private <T> ParameterProcessor processMultipleValues(String parameter, boolean isRequired, 
			BiFunction<HttpServletRequest, String, T> parser, List<BiPredicate<HttpServletRequest, T>> validators, Consumer<T> consumer) {

		String parameterValue = ValidatorUtils.getParameter(request, parameter, isRequired);

		if (parameterValue == null) {
			return this;
		}

		@SuppressWarnings("unchecked")
		T parsedValue = parser == null ? (T) parameterValue : parser.apply(request, parameterValue);

		if (parsedValue == null) {
			return this;
		}

		boolean validated = true;
		for (int i = 0; i < validators.size() && validated; i++) {
			validated = validated && validators.get(i).test(request, parsedValue);
		}

		if (validated) {
			consumer.accept(parsedValue);
		}

		return this;
	}

}
