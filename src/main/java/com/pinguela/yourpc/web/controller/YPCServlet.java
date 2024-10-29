package com.pinguela.yourpc.web.controller;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import com.pinguela.YPCException;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.listener.AbstractActionListener;
import com.pinguela.yourpc.web.model.Route;
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

	private static final Map<Class<? extends YPCServlet>, Map<String, AbstractActionListener>> LISTENERS = new ConcurrentHashMap<>();
	private Map<String, AbstractActionListener> instanceListeners;

	static {
		initialize();
	}

	private static void initialize() {
		Reflections reflections = new Reflections(AbstractActionListener.class.getPackageName());

		for (Class<? extends AbstractActionListener> clazz : 
			reflections.getSubTypesOf(AbstractActionListener.class)) {
			try {
				clazz.getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				logger.error(String.format(
						"Exception while instantiating {}: {}", clazz.getName(), e.getMessage()), e);
				throw new IllegalStateException(e);
			}
		}
	}

	@SafeVarargs
	public static void registerActionListener(AbstractActionListener listener, String action, Class<? extends YPCServlet>... servletClasses) {
		for (Class<? extends YPCServlet> servletClass : servletClasses) {
			LISTENERS.computeIfAbsent(servletClass, key -> {
				return new ConcurrentHashMap<>();
			}).put(action, listener);
		}
	}

	public YPCServlet() {
		instanceListeners = LISTENERS.get(this.getClass());
	}

	@Override
	protected final void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		Route route = processAction(req, resp);
		process(req, resp, route);
		RouterUtils.route(req, resp, route);
	}

	@Override
	protected final void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		doGet(req, resp);
	}

	private Route processAction(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		String action = (String) req.getParameter(Parameters.ACTION);
		Route route = null;

		if (action != null && instanceListeners.containsKey(action)) {
			try {
				route = instanceListeners.get(action).doAction(req, resp);
			} catch (YPCException e) {
				logger.error(e.getMessage(), e);
				throw new ServletException(e);
			}
		}

		return route;
	}

	protected abstract void process(HttpServletRequest req, HttpServletResponse resp, Route route)
			throws ServletException, IOException;

}
