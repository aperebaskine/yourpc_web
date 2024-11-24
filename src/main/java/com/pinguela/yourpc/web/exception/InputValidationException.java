package com.pinguela.yourpc.web.exception;

import com.pinguela.yourpc.web.constants.HttpErrorCodes;

@SuppressWarnings("serial")
public class InputValidationException extends YPCWebException {

	public InputValidationException() {
		this(null, null);
	}

	public InputValidationException(String message, Throwable cause) {
		super(HttpErrorCodes.SC_UNPROCESSABLE_ENTITY, message, cause);
	}

	public InputValidationException(String message) {
		this(message, null);
	}

	public InputValidationException(Throwable cause) {
		this(null, cause);
	}
}
