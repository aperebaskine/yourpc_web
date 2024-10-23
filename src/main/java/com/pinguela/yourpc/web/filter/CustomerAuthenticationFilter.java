package com.pinguela.yourpc.web.filter;

import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.web.util.Views;

@SuppressWarnings("serial")
public class CustomerAuthenticationFilter
extends AuthenticationFilter<Customer> {

	public CustomerAuthenticationFilter() {
		super(Customer.class, Views.USER_LOGIN);
	}
}
