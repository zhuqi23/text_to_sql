package com.text_to_sql.text_to_sql.pojo.dto;

import com.text_to_sql.text_to_sql.common.enumeration.code.UserStatus;
import com.text_to_sql.text_to_sql.common.enumeration.code.UserType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPageDTO {

	private Integer page;
	private Integer pageSize;

	private String name;
	private UserType type;
	private UserStatus status;
}
