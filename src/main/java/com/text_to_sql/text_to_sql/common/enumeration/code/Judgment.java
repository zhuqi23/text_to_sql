package com.text_to_sql.text_to_sql.common.enumeration.code;

import com.fasterxml.jackson.annotation.JsonValue;
import com.text_to_sql.text_to_sql.common.enumeration.CodeBasedEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Judgment implements CodeBasedEnum {

	WRONG(0, "错误"),
	CORRECT(1, "正确");

	private final Integer code;
	private final String description;

	public static Judgment fromCode(Integer code) {
		if (code == null) {
			return null;
		}
		for (Judgment judgment : values()) {
			if (judgment.getCode().equals(code)) {
				return judgment;
			}
		}
		throw new IllegalArgumentException("Invalid judgment code: " + code);
	}

	@Override
	@JsonValue
	public Integer getCode() {
		return code;
	}
}
