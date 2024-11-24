package com.pinguela.yourpc.web.exception;

/**
 * Root exception class for YPC web layer
 */
@SuppressWarnings("serial")
public class YPCWebException extends Exception {

	int errorCode;

	public YPCWebException(int errorCode) {
		super();
	}

	public YPCWebException(int errorCode, String message, Throwable cause) {
		super(message, cause);
	}

	public YPCWebException(int errorCode, String message) {
		super(message);
	}

	public YPCWebException(int errorCode, Throwable cause) {
		super(cause);
	}

}
