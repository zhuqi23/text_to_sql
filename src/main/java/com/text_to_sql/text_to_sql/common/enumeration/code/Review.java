package com.text_to_sql.text_to_sql.common.enumeration.code;

import com.fasterxml.jackson.annotation.JsonValue;
import com.text_to_sql.text_to_sql.common.enumeration.CodeBasedEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Review implements CodeBasedEnum {

	REVIEW_REQUESTED(0, "已申请复审"),
	NORMAL(1, "正常");

	private final Integer code;
	private final String description;

	// 根据code获取枚举对象
	public static Review fromCode(Integer code) {
		if (code == null) {
			return null;
		}
		for (Review review : values()) {
			if (review.getCode().equals(code)) {
				return review;
			}
		}
		throw new IllegalArgumentException("Invalid review code: " + code);
	}

	@Override
	@JsonValue
	public Integer getCode() {
		return code;
	}
}
