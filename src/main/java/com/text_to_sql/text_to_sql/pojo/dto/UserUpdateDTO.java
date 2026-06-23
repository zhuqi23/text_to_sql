package com.text_to_sql.text_to_sql.pojo.dto;

import com.text_to_sql.text_to_sql.common.enumeration.code.UserStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateDTO {

	private Long id;
	private String password;
	private String name;
	private UserStatus status;
}
