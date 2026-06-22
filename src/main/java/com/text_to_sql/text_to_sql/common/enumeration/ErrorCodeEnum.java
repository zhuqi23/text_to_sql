package com.text_to_sql.text_to_sql.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务错误码枚举
 */
@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {

	// ========== 通用错误 (1xxxx) ==========
	SUCCESS(0, "操作成功"),
	FAILED(1, "操作失败"),

	// ========== 参数错误 (2xxxx) ==========
	PARAM_ERROR(20001, "参数错误"),

	// ========== 知识点相关错误 (3xxxx) ==========
	KNOWLEDGE_EXISTS(30001, "知识点已存在"),
	KNOWLEDGE_HAS_QUESTIONS(30002, "有 %d 道相关题目, 不能删除"),

	// ========== 题目相关错误 (4xxxx) ==========
	QUESTION_NOT_FOUND(40001, "题目不存在"),

	// ========== 提交相关错误 (5xxxx) ==========
	SUBMIT_NOT_FOUND(50001, "提交不存在"),

	// ========== 用户相关错误 (6xxxx) ==========
	USER_NOT_FOUND(60001, "用户不存在"),
	INVALID_PASSWORD(60002, "密码错误"),
	INVALID_USER_TYPE(60003, "用户类型不匹配"),
	ACCOUNT_ALREADY_EXISTS(60004, "账号已存在"),
	REGISTER_FAILED(60005, "注册失败"),
	TOKEN_INVALID(60006, "令牌无效或已过期"),
	TOKEN_MISSING(60007, "缺少认证令牌"),

	// ========== 系统错误 (5xxxx) ==========
	SYSTEM_ERROR(50001, "服务器错误");

	private final Integer code;
	private final String message;

}
