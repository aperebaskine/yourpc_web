package com.pinguela.yourpc.web.listener;

import java.io.IOException;
import java.util.Collection;

import com.pinguela.yourpc.web.controller.YPCServlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface RequestActionListener {
	
	String getKey();
	
	Collection<Class<? extends YPCServlet>> getAssociatedServlets();
	
	String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
