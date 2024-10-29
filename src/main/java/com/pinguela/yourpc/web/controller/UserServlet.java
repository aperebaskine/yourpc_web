package com.pinguela.yourpc.web.controller;

import java.io.IOException;

import com.pinguela.yourpc.web.model.Route;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class UserServlet extends YPCServlet {
	   
    public UserServlet() {
        super();
    }

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp, Route route)
			throws ServletException, IOException {	
	}
	
}
