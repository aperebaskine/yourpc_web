package com.pinguela.yourpc.web.functions;

import java.io.IOException;

import javax.servlet.ServletException;

import com.pinguela.YPCException;
import com.pinguela.yourpc.web.exception.InputValidationException;

public interface ParameterProcessorRunnable {

	void run() throws ServletException, IOException, YPCException, InputValidationException;
	
}
