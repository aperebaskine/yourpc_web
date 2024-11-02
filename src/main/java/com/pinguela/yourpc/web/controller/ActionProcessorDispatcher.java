package com.pinguela.yourpc.web.controller;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections4.keyvalue.MultiKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import com.pinguela.YPCException;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.controller.processor.AbstractActionProcessor;
import com.pinguela.yourpc.web.controller.processor.ActionProcessor;
import com.pinguela.yourpc.web.model.Route;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

final class ActionProcessorDispatcher {

	private static Logger logger = LogManager.getLogger(ActionProcessorDispatcher.class);

	private static final ActionProcessorDispatcher INSTANCE = new ActionProcessorDispatcher(); 
	
	private static final AbstractActionProcessor INVALID_REQUEST_HANDLER = new AbstractActionProcessor() {
		@Override
		public void processAction(HttpServletRequest req, HttpServletResponse resp, Route route)
				throws ServletException, IOException, YPCException {
			logger.warn("Invalid action %s received in %s", req.getParameter(Parameters.ACTION), req.getRequestURI());
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	};
	
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
			try {
				register(clazz.getDeclaredConstructor().newInstance());
			} catch (Exception e) {
				logger.error(String.format(
						"Exception while instantiating {}: {}", clazz.getName(), e.getMessage()), e);
				throw new IllegalStateException(e);
			}
		}
	}

	private void register(AbstractActionProcessor processor) {

		Class<? extends AbstractActionProcessor> processorClass = processor.getClass();
		ActionProcessor annotation = processorClass.getAnnotation(ActionProcessor.class);

		if (annotation == null) {
			String errorMessage = String.format("Class %s must be annotated with @%s",
					processor.getClass().getSimpleName(), ActionProcessor.class.getSimpleName());
			logger.fatal(errorMessage);
			throw new IllegalStateException(errorMessage);
		}

		for (Class<? extends YPCServlet> servletClass : annotation.servlets()) {
			MultiKey<?> multikey = new MultiKey<>(servletClass, annotation.action());
			processors.put(multikey, processor);
		}
	}

	public final AbstractActionProcessor dispatch(YPCServlet servlet, String action) {
		MultiKey<?> key = new MultiKey<>(servlet.getClass(), action);
		return processors.getOrDefault(key, INVALID_REQUEST_HANDLER);
	}

}
