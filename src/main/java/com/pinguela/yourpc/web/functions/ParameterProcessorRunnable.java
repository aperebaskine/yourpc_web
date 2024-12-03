package com.pinguela.yourpc.web.functions;

import java.io.IOException;

import com.pinguela.YPCException;
import com.pinguela.yourpc.web.exception.InputValidationException;

import jakarta.servlet.ServletException;

public interface ParameterProcessorRunnable {

	void run() throws ServletException, IOException, YPCException, InputValidationException;
	
}
