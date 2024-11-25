package com.pinguela.yourpc.web.util;

import com.pinguela.yourpc.web.model.ErrorReport;

import jakarta.servlet.http.HttpServletRequest;

public class ValidatorUtils {
	
	public static Short validateShort(String shortStr, HttpServletRequest request) {
		try {
			return Short.valueOf(shortStr);
		} catch (NumberFormatException e) {
			ErrorReport errors = (ErrorReport) request.getAttribute("errors");
			errors.addFieldError(shortStr, shortStr);
			return null;
		}
	}

}
