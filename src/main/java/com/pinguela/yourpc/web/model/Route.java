package com.pinguela.yourpc.web.model;

import com.pinguela.yourpc.web.constants.RouteMethod;

public class Route {
	
	private String targetView;
	private RouteMethod routeMethod;
	
	public Route() {
	}
	
	public Route(String targetView, RouteMethod routeMethod) {
		super();
		this.targetView = targetView;
		this.routeMethod = routeMethod;
	}

	public String getTargetView() {
		return targetView;
	}

	public void setTargetView(String targetView) {
		this.targetView = targetView;
	}

	public RouteMethod getRouteMethod() {
		return routeMethod;
	}

	public void setRouteMethod(RouteMethod routeMethod) {
		this.routeMethod = routeMethod;
	}
	
}
