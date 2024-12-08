package com.pinguela.yourpc.web.controller.processor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.pinguela.YPCException;
import com.pinguela.yourpc.web.annotations.ActionProcessor;
import com.pinguela.yourpc.web.constants.Actions;
import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.constants.Parameters;
import com.pinguela.yourpc.web.exception.InputValidationException;
import com.pinguela.yourpc.web.model.CartItem;
import com.pinguela.yourpc.web.model.ErrorReport;
import com.pinguela.yourpc.web.util.OrderUtils;
import com.pinguela.yourpc.web.util.RouterUtils;
import com.pinguela.yourpc.web.util.ValidatorUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ActionProcessor(action = Actions.UPDATE_CART)
public class UpdateCartActionProcessor 
extends AbstractActionProcessor {

	@Override
	public void processAction(HttpServletRequest request, HttpServletResponse response, ErrorReport errors)
			throws ServletException, IOException, YPCException, InputValidationException {
		
		RouterUtils.setTargetView(request, "/DefaultServlet?action=view-cart");
		
		Long productId = ValidatorUtils.parseLong(request, Parameters.PRODUCT_ID, request.getParameter(Parameters.PRODUCT_ID));
		Integer quantity = ValidatorUtils.parseInt(request, Parameters.QUANTITY, request.getParameter(Parameters.QUANTITY));
		
		if (productId == null || quantity == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
		
		List<CartItem> cartItems = OrderUtils.getCart(request, response);
		List<CartItem> forRemoval = new ArrayList<CartItem>();
		boolean found = false;
		
		for (CartItem cartItem : cartItems) {
			if (cartItem.getProductId().equals(productId)) {
				found = true;
				if (quantity < 1) {
					forRemoval.add(cartItem);
				} else if (Boolean.TRUE.equals(ValidatorUtils.parseBoolean(
						request, Parameters.MODIFY_QUANTITY, request.getParameter(Parameters.MODIFY_QUANTITY)))) {
					cartItem.setQuantity(quantity);
				} else {
					cartItem.setQuantity(cartItem.getQuantity() + quantity);
				}
			}
		}
		
		cartItems.removeAll(forRemoval);
		
		if (!found && quantity > 0) {
			CartItem cartItem = new CartItem();
			cartItem.setProductId(productId);
			cartItem.setQuantity(quantity);
			cartItems.add(cartItem);
		}
		
		request.setAttribute(Attributes.CART, cartItems);
		OrderUtils.setCart(response, cartItems);
	}

}
