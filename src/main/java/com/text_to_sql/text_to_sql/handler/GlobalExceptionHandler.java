package com.text_to_sql.text_to_sql.handler;

import com.text_to_sql.text_to_sql.common.enumeration.ErrorCodeEnum;
import com.text_to_sql.text_to_sql.common.result.Result;
import com.text_to_sql.text_to_sql.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * 处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	/**
	 * 处理业务异常 (自己定义的异常)
	 */
	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Result handleBusinessException(BusinessException e) {
		log.warn("业务异常: code={}, message={}", e.getCode(), e.getMessage());
		return Result.error(e.getCode(), e.getMessage());
	}

	/**
	 * 处理业务异常 (运行时异常, 未知的异常)
	 * 业务异常: 违反业务逻辑/规则(参数不合法, 重复添加之类)
	 */
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Result handleRuntimeException(RuntimeException e) {
		log.error("业务异常：", e);
		return Result.error(e.getMessage());
	}

	/**
	 * 处理服务器异常
	 * 服务器异常: 本次请求进程断开(空指针之类), 还可以运行其他进程
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Result handleException(Exception e) {
		log.error("服务器异常：", e);
		return Result.error(ErrorCodeEnum.SYSTEM_ERROR);
	}
}
