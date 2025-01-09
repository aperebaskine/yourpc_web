package com.pinguela.yourpc.web.controller.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

import com.pinguela.yourpc.web.util.ParameterParser;

public class CurriedParameterProcessor {

	private HttpServletRequest request;
	private Function function;
	private List<Object> parsedParameters;
	
	public CurriedParameterProcessor(HttpServletRequest request, Function<?, ?> function) {
		this.request = request;
		this.function = function;
		this.parsedParameters = new ArrayList<Object>();
	}
	
	public CurriedParameterProcessor addParameter(String parameterName, Class<?> targetClass) {
		parsedParameters.add(ParameterParser.parse(request, parameterName, targetClass));
		return this;
	}
	
	public CurriedParameterProcessor addAttribute(Object attribute) {
		parsedParameters.add(attribute);
		return this;
	}
	
	public <R> R get() {
		int i = 0;
		Object link;
		
		while (true) {
			link = function.apply(parsedParameters.get(i));
			
			if (link instanceof Function) {
				function = (Function) link;
				i++;
			} else {
				return (R) link;
			}
		}
	}
	
}