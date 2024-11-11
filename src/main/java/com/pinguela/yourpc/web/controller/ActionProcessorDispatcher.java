package com.pinguela.yourpc.web.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections4.keyvalue.MultiKey;
import org.reflections.Reflections;

import com.pinguela.yourpc.web.annotations.ActionProcessor;
import com.pinguela.yourpc.web.controller.processor.AbstractActionProcessor;
import com.pinguela.yourpc.web.controller.processor.DefaultActionProcessor;
import com.pinguela.yourpc.web.util.ReflectionUtils;

final class ActionProcessorDispatcher {

	private static final ActionProcessorDispatcher INSTANCE = new ActionProcessorDispatcher();

	public static final ActionProcessorDispatcher getInstance() {
		return INSTANCE;
	}

	private final Map<MultiKey<?>, AbstractActionProcessor> processors = new ConcurrentHashMap<>();

	private ActionProcessorDispatcher() {
		initialize();
	}

	private void initialize() {
		Reflections reflections = new Reflections(AbstractActionProcessor.class.getPackageName());

		for (Class<? extends AbstractActionProcessor> clazz : 
			reflections.getSubTypesOf(AbstractActionProcessor.class)) {
			if (validate(clazz)) {
				register(clazz);
			}
		}
	}
	
	private boolean validate(Class<? extends AbstractActionProcessor> target) {
		return target.isAnnotationPresent(ActionProcessor.class);
	}

	private void register(Class<? extends AbstractActionProcessor> clazz) {

		ActionProcessor annotation = clazz.getAnnotation(ActionProcessor.class);

		for (Class<? extends YPCServlet> servletClass : annotation.servlets()) {
			MultiKey<?> key = new MultiKey<>(servletClass, annotation.action());
			processors.put(key, ReflectionUtils.instantiate(clazz));
		}
	}

	public final AbstractActionProcessor dispatch(YPCServlet servlet, String action) {
		MultiKey<?> key = new MultiKey<>(servlet.getClass(), action);
		return processors.getOrDefault(key, DefaultActionProcessor.getInstance());
	}

}
