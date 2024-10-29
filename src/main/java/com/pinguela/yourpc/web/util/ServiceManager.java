package com.pinguela.yourpc.web.util;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ServiceManager {

	private static Logger logger = LogManager.getLogger(ServiceManager.class);

	private static final Map<Class<?>, Object> SERVICES = new ConcurrentHashMap<>();
	
	@SuppressWarnings("unchecked")
	public static <S> S get(Class<S> service) {
		return (S) SERVICES.computeIfAbsent(service, key -> {
			return ServiceLoader.load(service).findFirst().orElseThrow(() -> {
				String message = 
						String.format("No service found for class %s.", service.getName());
				logger.error(message);
				throw new IllegalStateException(message);
			});
		});
	}

}
