package com.pinguela.yourpc.web.listener;

import java.util.Collection;

import com.pinguela.yourpc.web.controller.YPCServlet;

public abstract class AbstractRequestActionListener 
implements RequestActionListener {

	private String key;
	
	protected AbstractRequestActionListener(String key) {
		this.key = key;
	}
	
	@Override
	public String getKey() {
		return key;
	}
	
	public abstract Collection<Class<? extends YPCServlet>> getAssociatedServlets();
}
