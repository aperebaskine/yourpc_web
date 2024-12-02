package com.pinguela.yourpc.web.functions;

public interface TriPredicate<T, U, V> {
	
	boolean test(T t, U u, V v);

}
