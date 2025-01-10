package com.pinguela.yourpc.web.controller.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.servlet.http.HttpServletRequest;

import com.pinguela.yourpc.web.util.ParameterParser;

public class CurriedParameterProcessor<P, R> {

	private HttpServletRequest request;
	private List<Object> parsedParameters;
	
	private CurriedParameterProcessor(HttpServletRequest request, Class<R> returnType) {
		this.request = request;
		this.parsedParameters = new ArrayList<Object>();
	}
	
	public static <R> CurriedParameterProcessor<?, R> getInstance(HttpServletRequest request, Class<R> returnType) {
		return new CurriedParameterProcessor<Object, R>(request, returnType);
	}
	
	public <T> CurriedParameterProcessor<T, R> initialParameter(String parameterName,
			Class<T> targetClass, Predicate<T>... validators) {
		parsedParameters.add(ParameterParser.parse(request, parameterName, targetClass));
		return (CurriedParameterProcessor<T, R>) this;
	}
	
	public <T> CurriedParameterProcessor<T, Function<P, R>> addParameter(String parameterName, 
			Class<T> targetClass, Predicate<T>... validators) {
		parsedParameters.add(ParameterParser.parse(request, parameterName, targetClass));
		return (CurriedParameterProcessor<T, Function<P, R>>) this;
	}
	
	public R executeFunction(Function<P, R> function) {
		int i = 0;
		Object link;
		
		while (true) {
			link = function.apply((P) parsedParameters.get(i));
			
			if (link instanceof Function) {
				function = (Function) link;
				i++;
			} else {
				return (R) link;
			}
		}
	}
	
}