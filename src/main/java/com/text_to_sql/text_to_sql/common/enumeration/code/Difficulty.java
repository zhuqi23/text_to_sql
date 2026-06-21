package com.text_to_sql.text_to_sql.common.enumeration.code;

import com.fasterxml.jackson.annotation.JsonValue;
import com.text_to_sql.text_to_sql.common.enumeration.CodeBasedEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Difficulty implements CodeBasedEnum {

	OTHER(0, "其他"),
	EASY(1, "简单"),
	MEDIUM(2, "中等"),
	HARD(3, "困难");;

	private final Integer code;
	private final String description;

	// 根据code获取枚举对象
	public static Difficulty fromCode(Integer code) {
		if (code == null) {
			return null;
		}
		for (Difficulty difficulty : values()) {
			if (difficulty.getCode().equals(code)) {
				return difficulty;
			}
		}
		throw new IllegalArgumentException("Invalid difficulty code: " + code);
	}

	@Override
	@JsonValue
	public Integer getCode() {
		return code;
	}
}
