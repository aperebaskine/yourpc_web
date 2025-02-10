package com.pinguela.yourpc.web.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.pinguela.ServiceException;
import com.pinguela.YPCException;
import com.pinguela.yourpc.model.Address;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.service.ProductService;
import com.pinguela.yourpc.service.impl.ProductServiceImpl;
import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.constants.Cookies;
import com.pinguela.yourpc.web.model.CartItem;

public class OrderUtils {
	
	public static final String CHARSET = "UTF-8";

	private static ProductService productService = new ProductServiceImpl();
	private static Gson gson = new GsonBuilder()
			.registerTypeHierarchyAdapter(CartItem.class, CartItemSerializer.getInstance()).create();

	@SuppressWarnings("unchecked")
	public static List<CartItem> getCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, YPCException {

		List<CartItem> cartItems = (List<CartItem>) request.getAttribute(Attributes.CART);

		if (cartItems == null) {
			Cookie cart = CookieManager.getCookie(request, Cookies.CART);
			cartItems = gson.fromJson(cart == null ? "[]" : 
				URLDecoder.decode(cart.getValue(), CHARSET), new TypeToken<List<CartItem>>(){}.getType());
		}

		for (CartItem cartItem : cartItems) {
			try {
				cartItem.setProductDto(productService.findByIdLocalized(cartItem.getProductId(), LocaleUtils.getLocale(request)));
			} catch (ServiceException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} 
		}

		return cartItems;
	}

	public static void setCart(HttpServletResponse response, List<CartItem> cartItems) throws IOException {
		try {
			CookieManager.addCookie(response, Cookies.CART, URLEncoder.encode(gson.toJson(cartItems), CHARSET), 86400);
		} catch (UnsupportedEncodingException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	public static Address getShippingAddress(HttpServletRequest request) {
		Address a = (Address) request.getAttribute(Attributes.SHIPPING_ADDRESS);
		Customer c = (Customer) SessionManager.getAttribute(request, Attributes.CUSTOMER);

		if (a == null) {
			String idStr = CookieManager.getValue(request, Cookies.SHIPPING_ADDRESS);
			Integer id = Integer.valueOf(idStr);

			for (Address customerAddress : c.getAddresses()) {

				if (customerAddress.isDefault()) {
					a = customerAddress;
				}

				if (id != null && customerAddress.getId().equals(id)) {
					a = customerAddress;
					break;
				}
			}
		}

		return a;
	}

	public static Address getBillingAddress(HttpServletRequest request) {
		Address a = (Address) request.getAttribute(Attributes.BILLING_ADDRESS);
		Customer c = (Customer) SessionManager.getAttribute(request, Attributes.CUSTOMER);

		if (a == null) {
			String idStr = CookieManager.getValue(request, Cookies.BILLING_ADDRESS);
			Integer id = Integer.valueOf(idStr);

			for (Address customerAddress : c.getAddresses()) {

				if (customerAddress.isBilling()) {
					a = customerAddress;
				}

				if (id != null && customerAddress.getId().equals(id)) {
					a = customerAddress;
					break;
				}
			}
		}

		return a;
	}

	public static void setShippingAddress(HttpServletRequest request, HttpServletResponse response, Integer addressId) {
		Customer c = (Customer) SessionManager.getAttribute(request, Attributes.CUSTOMER);

		for (Address a : c.getAddresses()) {
			if (a.getId().equals(addressId)) {
				request.setAttribute(Attributes.SHIPPING_ADDRESS, a);
				CookieManager.addCookie(response, Cookies.SHIPPING_ADDRESS, a.getId().toString(), 86400);
			}
		}
	}

	public static void setBillingAddress(HttpServletRequest request, HttpServletResponse response, Integer addressId) {
		Customer c = (Customer) SessionManager.getAttribute(request, Attributes.CUSTOMER);

		for (Address a : c.getAddresses()) {
			if (a.getId().equals(addressId)) {
				request.setAttribute(Attributes.BILLING_ADDRESS, a);
				CookieManager.addCookie(response, Cookies.BILLING_ADDRESS, a.getId().toString(), 86400);
			}
		}
	}

}
