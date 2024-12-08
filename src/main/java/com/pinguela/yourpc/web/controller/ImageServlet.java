package com.pinguela.yourpc.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import org.apache.tomcat.util.codec.binary.Base64;

import com.pinguela.ServiceException;
import com.pinguela.yourpc.service.ImageFileService;
import com.pinguela.yourpc.service.impl.ImageFileServiceImpl;
import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.util.ValidatorUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ImageServlet
 */
@SuppressWarnings("serial")
public class ImageServlet extends HttpServlet {
    
	private ImageFileService service;
	
    public ImageServlet() {
    	this.service = new ImageFileServiceImpl();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter(Parameters.IMAGE_TYPE);
		Integer key = ValidatorUtils.parseInt(request, Parameters.IMAGE_KEY, request.getParameter(Parameters.IMAGE_KEY));
		
		List<String> entries = Collections.emptyList();
		
		try {
			entries = service.getFilePaths(type, key);
		} catch (ServiceException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		
		List<String> encoded = new ArrayList<String>();
		
		for (String entry : entries) {
			ImageInputStream is = ImageIO.createImageInputStream(new File(entry));
			
			byte[] bytes = new byte[(int) is.length()];
			is.read(bytes);
			
			String base64 = Base64.encodeBase64String(bytes);
			encoded.add(base64);
		}
		
		request.setAttribute(Attributes.IMAGES, encoded);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
