package com.pinguela.yourpc.web.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorReport {

	private List<String> globalErrors;
	private Map<String, List<String>> fieldErrors;
	
	public ErrorReport() {
		globalErrors = new ArrayList<String>();
		fieldErrors = new HashMap<String, List<String>>();
	}
	
	public boolean hasErrors() {
		return !(globalErrors.isEmpty() && fieldErrors.isEmpty());
	}
	
	public List<String> getGlobalErrors() {
		return globalErrors;
	}
	
	public List<String> getFieldErrors(String fieldName) {
		return fieldErrors.getOrDefault(fieldName, Collections.emptyList());
	}
	
	public void addGlobalError(String errorCode) {
		globalErrors.add(errorCode);
	}
	
	public void addFieldError(String fieldName, String errorCode) {
		fieldErrors.computeIfAbsent(fieldName, key -> new ArrayList<String>()).add(errorCode);
	}
	
	public void removeGlobalError(String errorCode) {
		globalErrors.remove(errorCode);
	}
	
	public void removeFieldError(String fieldName, String errorCode) {
		List<String> errors = fieldErrors.get(fieldName);
		
		if (errors == null) {
			return;
		}
		
		errors.remove(errorCode);
		
		if (errors.isEmpty()) {
			fieldErrors.remove(fieldName);
		}
	}
	
	public void clear() {
		globalErrors.clear();
		fieldErrors.clear();
	}
	
	public void clearGlobalErrors() {
		globalErrors.clear();
	}
	
	public void clearFieldErrors(String fieldName) {
		fieldErrors.remove(fieldName);
	}
	
}
