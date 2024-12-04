package com.pinguela.yourpc.web.exception;

@SuppressWarnings("serial")
public class ParameterProcessorException extends YPCWebException {

	public ParameterProcessorException(int errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
		// TODO Auto-generated constructor stub
	}

	public ParameterProcessorException(int errorCode, String message) {
		super(errorCode, message);
		// TODO Auto-generated constructor stub
	}

	public ParameterProcessorException(int errorCode, Throwable cause) {
		super(errorCode, cause);
		// TODO Auto-generated constructor stub
	}

	public ParameterProcessorException(int errorCode) {
		super(errorCode);
		// TODO Auto-generated constructor stub
	}
	
	

}
