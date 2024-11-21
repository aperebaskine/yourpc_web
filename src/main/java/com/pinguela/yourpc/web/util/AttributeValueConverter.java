package com.pinguela.yourpc.web.util;

import com.pinguela.yourpc.model.constants.AttributeDataTypes;

public class AttributeValueConverter {

	// TODO: Maybe there's a better way to do this
	public static Object convert(String dataType, String value) {
		switch (dataType) {
		case AttributeDataTypes.BIGINT:
			return Long.valueOf(value);
		case AttributeDataTypes.VARCHAR:
			return value;
		case AttributeDataTypes.DECIMAL:
			return Double.valueOf(value);
		case AttributeDataTypes.BOOLEAN:
			return Boolean.valueOf(value);
		default:
			throw new IllegalArgumentException(String.format("Unrecognized data type: %s", dataType));
		}
	}

}
