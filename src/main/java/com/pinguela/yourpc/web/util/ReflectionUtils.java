package com.pinguela.yourpc.web.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReflectionUtils {
	
	private static Logger logger = LogManager.getLogger(ReflectionUtils.class);
	
	public static <T> T instantiate(Class<T> target, Object... args) {
		try {
			return target.getDeclaredConstructor(getClasses(args)).newInstance(args);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			logger.error("{} thrown while instantiating {}. Message: {}",
					e.getClass().getName(), target.getName(), e.getMessage(), e);
			throw new IllegalArgumentException(e);
		}
	}
	
	private static Class<?>[] getClasses(Object... objects) {
		Class<?>[] classes = new Class<?>[objects.length];
		
		for (int i = 0; i < objects.length; i++) {
			classes[i] = Objects.requireNonNull(objects[i], "Cannot instantiate object using null arguments.").getClass();
		}
		
		return classes;
	}

}
