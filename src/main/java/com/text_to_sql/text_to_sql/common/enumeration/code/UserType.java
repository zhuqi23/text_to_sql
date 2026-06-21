package com.text_to_sql.text_to_sql.common.enumeration.code;

import com.fasterxml.jackson.annotation.JsonValue;
import com.text_to_sql.text_to_sql.common.enumeration.CodeBasedEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserType implements CodeBasedEnum {
    
    USER(0, "用户"),
    ADMIN(1, "管理员");
    
    private final Integer code;
    private final String description;

	// 根据code获取枚举对象
    public static UserType fromCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (UserType userType : values()) {
            if (userType.getCode().equals(code)) {
                return userType;
            }
        }
        throw new IllegalArgumentException("Invalid user type code: " + code);
    }

	@Override
	@JsonValue
	public Integer getCode() {
		return code;
	}
}
