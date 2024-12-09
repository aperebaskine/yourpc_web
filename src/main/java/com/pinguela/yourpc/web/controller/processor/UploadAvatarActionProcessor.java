package com.pinguela.yourpc.web.controller.processor;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.fileupload2.core.FileItemInput;
import org.apache.commons.fileupload2.core.FileItemInputIterator;
import org.apache.commons.fileupload2.jakarta.servlet5.JakartaServletDiskFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.pinguela.YPCException;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.model.ImageEntry;
import com.pinguela.yourpc.service.ImageFileService;
import com.pinguela.yourpc.service.impl.ImageFileServiceImpl;
import com.pinguela.yourpc.web.annotations.ActionProcessor;
import com.pinguela.yourpc.web.constants.Actions;
import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.controller.UserServlet;
import com.pinguela.yourpc.web.exception.InputValidationException;
import com.pinguela.yourpc.web.model.ErrorReport;
import com.pinguela.yourpc.web.util.RouterUtils;
import com.pinguela.yourpc.web.util.SessionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ActionProcessor(action = Actions.UPLOAD_AVATAR, servlets = UserServlet.class)
public class UploadAvatarActionProcessor extends AbstractActionProcessor {
	
	private JakartaServletDiskFileUpload fileUpload = new JakartaServletDiskFileUpload();
	private ImageFileService fileService = new ImageFileServiceImpl();

	@Override
	public void processAction(HttpServletRequest request, HttpServletResponse response, ErrorReport errors)
			throws ServletException, IOException, YPCException, InputValidationException {

		RouterUtils.setTargetView(request, "/user/UserServlet?action=user-details");

		if (!ServletFileUpload.isMultipartContent(request)) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		FileItemInputIterator iterator = fileUpload.getItemIterator(request);

		if (!iterator.hasNext()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		FileItemInput fileInput = iterator.next();

		String contentType = fileInput.getContentType();

		if (contentType == null || !contentType.startsWith("image")) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		InputStream is = fileInput.getInputStream();
		ImageEntry entry = new ImageEntry(ImageIO.read(is), null);
		
		Customer c = (Customer) SessionManager.getAttribute(request, Attributes.CUSTOMER);
		
		List<String> paths = fileService.getFilePaths(Attributes.AVATAR, c.getId());
		if (!paths.isEmpty()) {
			entry.setPath(paths.get(0));
		}
		
		fileService.update(Attributes.AVATAR, c.getId(), entry);

	}

}
