package com.text_to_sql.text_to_sql.pojo.entity;

import com.text_to_sql.text_to_sql.common.enumeration.code.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

	// id自增, 账号account, 密码password, 昵称name, 类型type(0为用户,1为管理员), 创建时间
	private Long id;
	private String account;
	private String password;
	private String name;
	private UserType type;

//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")  // 有全局时间格式, 配置使用后端时间格式均为 yyyy-MM-dd HH:mm:ss
	private LocalDateTime createTime;
}
