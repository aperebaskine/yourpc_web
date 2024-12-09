package com.pinguela.yourpc.web.util;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.pinguela.yourpc.web.model.CartItem;

public class CartItemSerializer implements JsonSerializer<CartItem> {

	private static final CartItemSerializer INSTANCE = new CartItemSerializer();

	private CartItemSerializer() {
	}

	public static final CartItemSerializer getInstance() {
		return INSTANCE;
	}

	@Override
	public JsonElement serialize(CartItem src, Type typeOfSrc, JsonSerializationContext context) {
		
		JsonObject jsonObject = new JsonObject();
		
		jsonObject.addProperty("productId", src.getProductId());
		jsonObject.addProperty("quantity", src.getQuantity());
		
		return jsonObject;
	}
	
}
