package com.text_to_sql.text_to_sql.pojo.vo;

import com.text_to_sql.text_to_sql.common.enumeration.code.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserListVO {

	private Long id;
	private String account;
	private String name;
	private String type;
	private UserStatus status;
	private LocalDateTime createTime;

}
