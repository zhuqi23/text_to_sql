package com.text_to_sql.text_to_sql.pojo.dto;

import com.text_to_sql.text_to_sql.common.enumeration.code.UserType;
import lombok.Data;

@Data
public class RegisterDTO {

	private String account;
	private String password;
	private String name;
	private UserType type;
}
