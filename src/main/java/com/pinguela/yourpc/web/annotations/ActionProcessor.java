package com.pinguela.yourpc.web.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.pinguela.yourpc.web.controller.DefaultServlet;
import com.pinguela.yourpc.web.controller.YPCServlet;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ActionProcessor {
	
	String action();
	Class<? extends YPCServlet>[] servlets() default DefaultServlet.class;
	
}
