package com.pinguela.yourpc.web.controller.processor;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinguela.YPCException;
import com.pinguela.yourpc.model.Address;
import com.pinguela.yourpc.web.annotations.ActionProcessor;
import com.pinguela.yourpc.web.constants.Actions;
import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.constants.Views;
import com.pinguela.yourpc.web.controller.UserServlet;
import com.pinguela.yourpc.web.exception.InputValidationException;
import com.pinguela.yourpc.web.model.CartItem;
import com.pinguela.yourpc.web.model.ErrorReport;
import com.pinguela.yourpc.web.util.OrderUtils;
import com.pinguela.yourpc.web.util.RouterUtils;

@ActionProcessor(action = Actions.REVIEW_ORDER, servlets = UserServlet.class)
public class ReviewOrderActionProcessor 
extends AbstractActionProcessor {

	@Override
	public void processAction(HttpServletRequest request, HttpServletResponse response, ErrorReport errors)
			throws ServletException, IOException, YPCException, InputValidationException {
		
		Address shippingAddress = OrderUtils.getShippingAddress(request);
		Address billingAddress = OrderUtils.getBillingAddress(request);
		
		OrderUtils.setShippingAddress(request, response, shippingAddress.getId());
		OrderUtils.setBillingAddress(request, response, billingAddress.getId());
		
		List<CartItem> cartItems = OrderUtils.getCart(request, response);
		request.setAttribute(Attributes.CART, cartItems);
		
		RouterUtils.setTargetView(request, Views.ORDER_VIEW);
	}

}
