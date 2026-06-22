package com.text_to_sql.text_to_sql.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.text_to_sql.text_to_sql.common.enumeration.CodeBasedEnum;

import java.io.IOException;
import java.lang.reflect.Method;

public class CodeBasedEnumDeserializer extends JsonDeserializer<CodeBasedEnum> implements ContextualDeserializer {

	private Class<?> targetClass;

	@Override
	public CodeBasedEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		String value = p.getValueAsString();
		
		if (value == null || value.isEmpty()) {
			return null;
		}

		Integer code;
		try {
			code = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new IOException("Invalid enum code: " + value, e);
		}

		try {
			Method fromCodeMethod = targetClass.getMethod("fromCode", Integer.class);
			return (CodeBasedEnum) fromCodeMethod.invoke(null, code);
		} catch (Exception e) {
			throw new IOException("Failed to deserialize enum: " + code, e);
		}
	}

	@Override
	public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
		CodeBasedEnumDeserializer deserializer = new CodeBasedEnumDeserializer();
		deserializer.targetClass = property.getType().getRawClass();
		return deserializer;
	}
}
