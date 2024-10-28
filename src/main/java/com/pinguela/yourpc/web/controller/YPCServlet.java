package com.pinguela.yourpc.web.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.constants.RouteMethod;
import com.pinguela.yourpc.web.listener.AbstractRequestActionListener;
import com.pinguela.yourpc.web.listener.RequestActionListener;
import com.pinguela.yourpc.web.util.RouterUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class YPCServlet
 */
@SuppressWarnings("serial")
public abstract class YPCServlet extends HttpServlet {

	private static Logger logger = LogManager.getLogger(YPCServlet.class);

	private static final Map<Class<? extends YPCServlet>, List<RequestActionListener>> LISTENERS = initListeners();
	private Map<String, RequestActionListener> listeners;

	private static Map<Class<? extends YPCServlet>, List<RequestActionListener>> initListeners() {
		Map<Class<? extends YPCServlet>, List<RequestActionListener>> listeners = new HashMap<>();
		Reflections reflections = new Reflections(AbstractRequestActionListener.class.getPackageName());

		for (Class<? extends RequestActionListener> clazz : 
			reflections.getSubTypesOf(AbstractRequestActionListener.class)) {
			try {
				RequestActionListener listener = clazz.getDeclaredConstructor().newInstance();
				for (Class<? extends YPCServlet> servletClass : listener.getAssociatedServlets()) {
					List<RequestActionListener> listenerList = listeners.computeIfAbsent(servletClass, key -> {
						return new ArrayList<>();
					});
					listenerList.add(listener);
				}
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				logger.fatal(e.getMessage(), e);
				throw new ExceptionInInitializerError(e);
			}
		}
		
		return Collections.unmodifiableMap(listeners);
	}

	private Map<String, RequestActionListener> getInstanceListeners() {
		Map<String, RequestActionListener> listeners = new HashMap<String, RequestActionListener>();
		for (RequestActionListener listener : LISTENERS.get(this.getClass())) {
			listeners.put(listener.getKey(), listener);
		}
		return listeners;
	}

	public YPCServlet() {
		listeners = getInstanceListeners();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String action = (String) req.getParameter(Parameters.ACTION);
		
		String targetView = null;
		RouteMethod routeMethod = RouteMethod.REDIRECT;

		if (action != null && listeners.containsKey(action)) {
			routeMethod = RouteMethod.FORWARD;
			targetView = listeners.get(action).execute(req, resp);
		}
		
		RouterUtils.route(req, resp, routeMethod, targetView);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
