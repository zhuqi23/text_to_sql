package com.text_to_sql.text_to_sql.pojo.vo;

import com.text_to_sql.text_to_sql.common.enumeration.code.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginVO {

	private Long id;
	private String account;
	private String name;
	private String token;
	private UserType type;
}
