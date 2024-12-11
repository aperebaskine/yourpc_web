package com.pinguela.yourpc.web.controller.processor;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;

import com.pinguela.ServiceException;
import com.pinguela.YPCException;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.service.ImageFileService;
import com.pinguela.yourpc.service.impl.ImageFileServiceImpl;
import com.pinguela.yourpc.web.annotations.ActionProcessor;
import com.pinguela.yourpc.web.constants.Actions;
import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.controller.UserServlet;
import com.pinguela.yourpc.web.exception.InputValidationException;
import com.pinguela.yourpc.web.model.ErrorReport;
import com.pinguela.yourpc.web.util.SessionManager;

@ActionProcessor(action = Actions.DOWNLOAD_AVATAR, servlets = UserServlet.class)
public class DownloadAvatarActionProcessor
extends AbstractActionProcessor {

	private ImageFileService service;

	public DownloadAvatarActionProcessor() {
		this.service = new ImageFileServiceImpl();
	}

	@Override
	public void processAction(HttpServletRequest request, HttpServletResponse response, ErrorReport errors)
			throws ServletException, IOException, YPCException, InputValidationException {
		Customer c = (Customer) SessionManager.getAttribute(request, Attributes.CUSTOMER);

		List<String> entries = Collections.emptyList();

		try {
			entries = service.getFilePaths(Attributes.AVATAR, c.getId());
		} catch (ServiceException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

		if (!entries.isEmpty()) {
			ImageInputStream is = ImageIO.createImageInputStream(new File(entries.get(0)));

			byte[] bytes = new byte[(int) is.length()];
			is.read(bytes);

			String base64 = Base64.encodeBase64String(bytes);
			request.setAttribute(Attributes.AVATAR, base64);
		}
	}

}
