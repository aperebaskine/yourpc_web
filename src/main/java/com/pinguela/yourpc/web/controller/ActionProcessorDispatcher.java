package com.pinguela.yourpc.web.controller;

import java.util.Map;

import org.apache.commons.collections4.keyvalue.MultiKey;

import com.pinguela.yourpc.web.annotations.ActionProcessor;
import com.pinguela.yourpc.web.controller.processor.AbstractActionProcessor;
import com.pinguela.yourpc.web.controller.processor.DefaultActionProcessor;
import com.pinguela.yourpc.web.util.ClassHierarchyRegistryBuilder;

final class ActionProcessorDispatcher {

	private static final ActionProcessorDispatcher INSTANCE = new ActionProcessorDispatcher();

	public static final ActionProcessorDispatcher getInstance() {
		return INSTANCE;
	}

	private final Map<MultiKey<?>, AbstractActionProcessor> processors;

	private ActionProcessorDispatcher() {
		processors = ClassHierarchyRegistryBuilder.createClassHierarchyRegistry(
				AbstractActionProcessor.class, false, ActionProcessor.class);
	}

	public final AbstractActionProcessor dispatch(String action, YPCServlet servlet) {
		MultiKey<?> key = new MultiKey<>(action, servlet.getClass());
		return processors.getOrDefault(key, DefaultActionProcessor.getInstance());
	}

}
