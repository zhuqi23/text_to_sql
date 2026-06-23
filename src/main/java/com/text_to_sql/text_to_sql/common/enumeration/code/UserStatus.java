package com.text_to_sql.text_to_sql.common.enumeration.code;

import com.fasterxml.jackson.annotation.JsonValue;
import com.text_to_sql.text_to_sql.common.enumeration.CodeBasedEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus implements CodeBasedEnum {

	BANNED(0, "拉黑"),
	NORMAL(1, "正常");

	private final Integer code;
	private final String description;

	public static UserStatus fromCode(Integer code) {
		if (code == null) {
			return null;
		}
		for (UserStatus status : values()) {
			if (status.getCode().equals(code)) {
				return status;
			}
		}
		throw new IllegalArgumentException("Invalid user status code: " + code);
	}

	@Override
	@JsonValue
	public Integer getCode() {
		return code;
	}
}
