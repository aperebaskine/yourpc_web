package com.pinguela.yourpc.web.controller.processor;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.ServiceException;
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

@ActionProcessor(action = Actions.UPLOAD_AVATAR, servlets = UserServlet.class)
public class UploadAvatarActionProcessor extends AbstractActionProcessor {
	
	private static Logger logger = LogManager.getLogger(UploadAvatarActionProcessor.class);
	
	private ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
	private ImageFileService fileService = new ImageFileServiceImpl();

	@Override
	public void processAction(HttpServletRequest request, HttpServletResponse response, ErrorReport errors)
			throws ServletException, IOException, YPCException, InputValidationException {

		try {
			RouterUtils.setTargetView(request, "/user/UserServlet?action=user-details");

			if (!ServletFileUpload.isMultipartContent(request)) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			FileItemIterator iterator = fileUpload.getItemIterator(request);

			if (!iterator.hasNext()) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			FileItemStream fileInput = iterator.next();

			String contentType = fileInput.getContentType();

			if (contentType == null || !contentType.startsWith("image")) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
			
			InputStream is = fileInput.openStream();
			ImageEntry entry = new ImageEntry(ImageIO.read(is), null);
			
			Customer c = (Customer) SessionManager.getAttribute(request, Attributes.CUSTOMER);
			
			List<String> paths = fileService.getFilePaths(Attributes.AVATAR, c.getId());
			if (!paths.isEmpty()) {
				entry.setPath(paths.get(0));
			}
			
			fileService.update(Attributes.AVATAR, c.getId(), entry);
		} catch (FileUploadException e) {
			logger.error(e);
			throw new ServiceException(e);
		}

	}

}
