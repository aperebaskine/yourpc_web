package com.pinguela.yourpc.web.controller.processor;

import java.io.IOException;
import java.util.List;

import com.pinguela.YPCException;
import com.pinguela.yourpc.model.Address;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.model.CustomerOrder;
import com.pinguela.yourpc.model.OrderLine;
import com.pinguela.yourpc.service.CustomerOrderService;
import com.pinguela.yourpc.service.impl.CustomerOrderServiceImpl;
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
import com.pinguela.yourpc.web.util.SessionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ActionProcessor(action = Actions.CONFIRM_ORDER, servlets = UserServlet.class)
public class ConfirmOrderActionProcessor extends AbstractActionProcessor {
	
	private CustomerOrderService service;
	
	public ConfirmOrderActionProcessor() {
		this.service = new CustomerOrderServiceImpl();
	}

	@Override
	public void processAction(HttpServletRequest request, HttpServletResponse response, ErrorReport errors)
			throws ServletException, IOException, YPCException, InputValidationException {
		
		CustomerOrder co = new CustomerOrder();
		
		Customer c = (Customer) SessionManager.getAttribute(request, Attributes.CUSTOMER);
		Address shippingAddress = OrderUtils.getShippingAddress(request);
		Address billingAddress = OrderUtils.getBillingAddress(request);
		
		List<CartItem> cartItems = OrderUtils.getCart(request, response);
		
		co.setCustomerId(c.getId());
		co.setState("PND");
		co.setShippingAddressId(shippingAddress.getId());
		co.setBillingAddressId(billingAddress.getId());
		co.setTotalPrice(calculateTotalPrice(cartItems));
		
		for (CartItem cartItem : cartItems) {
			OrderLine ol = new OrderLine();
			ol.setProductId(cartItem.getProductId().intValue());
			ol.setPurchasePrice(cartItem.getProductDto().getPurchasePrice());
			ol.setSalePrice(cartItem.getProductDto().getSalePrice());
			ol.setQuantity(cartItem.getQuantity().shortValue());
			co.getOrderLines().add(ol);
		}
		
		Long id = service.create(co);
		
		if (id == null) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} else {
			RouterUtils.setTargetView(request, "/user/UserServlet?action=user-details");
		}
	}
	
	private double calculateTotalPrice(List<CartItem> cart) {
		double total = 0.0d;
		
		for (CartItem cartItem : cart) {
			total += (cartItem.getProductDto().getSalePrice() * cartItem.getQuantity());
		}
		
		return total;
	}

}
