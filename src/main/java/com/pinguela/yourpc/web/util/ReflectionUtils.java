package com.pinguela.yourpc.web.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public final class ReflectionUtils {

	private ReflectionUtils() {
	}

	public static Class<?> getClass(Type type) {
		Class<?> clazz = null;
		try {
			clazz = Class.forName(getRawTypeName(type));
		} catch (ClassNotFoundException e) {
			// This exception should never be thrown as the Type object already represents a class
		}
		return clazz;
	}

	public static String getRawTypeName(Type type) {
		return type instanceof ParameterizedType ?
				((ParameterizedType) type).getRawType().getTypeName() :
					type.getTypeName();
	}

	public static Type[] getTypeParameterBounds(Class<?> clazz) {

		TypeVariable<?>[] typeVariables = clazz.getTypeParameters();

		if (typeVariables.length == 0) {
			Type superclassType = clazz.getGenericSuperclass();

			if (superclassType == null) {
				return new Type[0];
			}

			if (superclassType instanceof ParameterizedType) {
				Type[] superclassTypeArguments = ((ParameterizedType) superclassType).getActualTypeArguments();
				if (superclassTypeArguments.length > 0) {
					return superclassTypeArguments;
				}
			}
			// Find the closest class in the upper hierarchy with type parameters
			return getTypeParameterBounds(getClass(superclassType));
		}
		return typeVariables[0].getBounds();
	}

}
