package com.pinguela.yourpc.web.controller;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.pinguela.yourpc.model.dto.AttributeDTO;

public class AttributeSerializer implements JsonSerializer<AttributeDTO<?>> {
	
	private static final Gson DEFAULT_GSON = new GsonBuilder().create();
	private static final AttributeSerializer INSTANCE = new AttributeSerializer();

	private AttributeSerializer() {
	}

	public static final AttributeSerializer getInstance() {
		return INSTANCE;
	}

	@Override
	public JsonElement serialize(AttributeDTO<?> src, Type typeOfSrc, JsonSerializationContext context) {
		
		JsonElement json = DEFAULT_GSON.toJsonTree(src, typeOfSrc);
		
		if (json.isJsonObject()) {
			JsonObject jsonObject = json.getAsJsonObject();
			jsonObject.addProperty("dataTypeIdentifier", src.getDataTypeIdentifier());
			jsonObject.addProperty("valueHandlingMode", src.getValueHandlingMode());
		}

		return json;
	}

}
