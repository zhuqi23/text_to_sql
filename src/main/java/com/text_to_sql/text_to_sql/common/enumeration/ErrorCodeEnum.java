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

	// ========== 系统错误 (5xxxx) ==========
	SYSTEM_ERROR(50001, "服务器错误");

	private final Integer code;
	private final String message;

}
