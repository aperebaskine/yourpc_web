package com.pinguela.yourpc.web.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

import org.apache.commons.collections4.keyvalue.MultiKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

public class ClassHierarchyRegistryBuilder {

	private static Logger logger = LogManager.getLogger(ClassHierarchyRegistryBuilder.class);

	public static <T> Set<Class<? extends T>> getSubclassesInPackage(Class<T> superclass) {
		Reflections reflections = new Reflections(superclass.getPackage().getName());
		return reflections.getSubTypesOf(superclass);
	}

	public static <T> Map<MultiKey<?>, T> createClassHierarchyRegistry(Class<T> superclass, boolean includeSuperclass, 
			Predicate<Class<? extends T>> validatingFunction, BiConsumer<Map<MultiKey<?>, T>, T> mappingFunction) {

		Set<Class<? extends T>> classes = getSubclassesInPackage(superclass);

		if (includeSuperclass) {
			classes.add(superclass);
		}

		Map<MultiKey<?>, T> mappedClasses = 
				new ConcurrentHashMap<MultiKey<?>, T>((int) (classes.size() + 1), 1);

		for (Class<? extends T> clazz : classes) {
			int mod = clazz.getModifiers();
			if (Modifier.isAbstract(mod) || Modifier.isInterface(mod)) {
				continue;
			}

			if (!validatingFunction.test(clazz)) {
				logger.warn("Validation failed for class {}.", clazz.getName());
				continue;
			}

			mappingFunction.accept(mappedClasses, ReflectionUtils.instantiate(clazz));
		}

		return mappedClasses;
	}

	public static <T> Map<MultiKey<?>, T> createClassHierarchyRegistry(Class<T> superclass, boolean includeSuperclass, 
			Class<? extends Annotation> annotationClass, BiConsumer<Map<MultiKey<?>, T>, T> mappingFunction) {
		return createClassHierarchyRegistry(superclass, includeSuperclass, checkAnnotation(annotationClass), mappingFunction);
	}

	public static <T> Map<MultiKey<?>, T> createClassHierarchyRegistry(Class<T> superclass, boolean includeSuperclass, 
			Class<? extends Annotation> annotationClass) {
		return createClassHierarchyRegistry(superclass, includeSuperclass, 
				checkAnnotation(annotationClass), mapByAnnotation(annotationClass));
	}

	private static <T> Predicate<Class<? extends T>> checkAnnotation(final Class<? extends Annotation> annotationClass) {
		return (clazz) -> {
			if (!clazz.isAnnotationPresent(annotationClass)) {
				logger.warn("Class {} is not annotated with {}. Skipping registration.", 
						clazz.getName(), annotationClass.getName());
				return false;
			} else {
				return true;
			}
		};
	}

	private static <T> BiConsumer<Map<MultiKey<?>, T>, T> mapByAnnotation(final Class<? extends Annotation> annotationClass) {
		return (map, instance) -> {
			List<MultiKey<?>> keys = computeKeysFromAnnotation(instance.getClass().getAnnotation(annotationClass));
			for (MultiKey<?> key : keys) {
				map.put(key, instance);
			}
		};
	}

	private static List<MultiKey<?>> computeKeysFromAnnotation(Annotation annotation) {
		List<MultiKey<?>> keys = new ArrayList<MultiKey<?>>();

		Method[] annotationMethods = annotation.annotationType().getDeclaredMethods();
		Arrays.sort(annotationMethods, Comparator.comparing(Method::getName));
		computeKeysFromAnnotation(annotation, annotationMethods, 0, keys, new ArrayList<Object>());

		return keys;
	}

	private static void computeKeysFromAnnotation(Annotation annotation, Method[] annotationMethods,
			int index, List<MultiKey<?>> keys, List<Object> components) {

		if (index >= annotationMethods.length) {
			MultiKey<?> key = new MultiKey<>(components.toArray());
			keys.add(key);
			return;
		}

		Method method = annotationMethods[index++];
		Object value;

		try {
			value = method.invoke(annotation);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			logger.error("Unexpected error while calling {} from annotation {}", 
					method.getName(), annotation.getClass().getName());
			throw new IllegalStateException(e);
		}

		if (value.getClass().isArray()) {
			computeKeysFromAnnotationHandleArray(annotation, annotationMethods, index, (Object[]) value, keys, components);
		} else {
			components.add(value);
			computeKeysFromAnnotation(annotation, annotationMethods, index, keys, components);
		}
	}
	
	private static void computeKeysFromAnnotationHandleArray(Annotation annotation, Method[] annotationMethods,
			int index, Object[] values, List<MultiKey<?>> keys, List<Object> components) {
		
		if (values.length == 0) {
			computeKeysFromAnnotation(annotation, annotationMethods, index, keys, components);
			return;
		}
		 
		for (int i = 1; i < values.length; i++) {
			List<Object> componentsForArrayValue = new ArrayList<Object>(components);
			componentsForArrayValue.add(values[i]);
			computeKeysFromAnnotation(annotation, annotationMethods, index, keys, componentsForArrayValue);
		}
		
		// Prevent list duplication for single value by handling it here
		components.add(values[0]);
		computeKeysFromAnnotation(annotation, annotationMethods, index, keys, components);
	}

}
