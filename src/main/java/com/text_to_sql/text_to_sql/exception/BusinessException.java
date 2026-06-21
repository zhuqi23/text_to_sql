package com.text_to_sql.text_to_sql.exception;

import com.text_to_sql.text_to_sql.common.enumeration.ErrorCodeEnum;
import lombok.Getter;

/**
 * 业务异常类
 */
@Getter
public class BusinessException extends RuntimeException {

	private final Integer code;
	private final String message;

	// 无参
	public BusinessException(ErrorCodeEnum errorCodeEnum) {
		super(errorCodeEnum.getMessage());
		this.code = errorCodeEnum.getCode();
		this.message = errorCodeEnum.getMessage();
	}

	// 有参
	public BusinessException(ErrorCodeEnum errorCodeEnum, Object... args) {
		super(String.format(errorCodeEnum.getMessage(), args));
		this.code = errorCodeEnum.getCode();
		this.message = String.format(errorCodeEnum.getMessage(), args);
	}

}
