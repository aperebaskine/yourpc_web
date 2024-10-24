package com.pinguela.yourpc.web.filter;

import com.pinguela.yourpc.model.Employee;
import com.pinguela.yourpc.web.constants.Views;

@SuppressWarnings("serial")
public class EmployeeAuthenticationFilter 
extends AuthenticationFilter<Employee> {

	public EmployeeAuthenticationFilter() {
		super(Employee.class, Views.EMPLOYEE_LOGIN);
	}
	
}
