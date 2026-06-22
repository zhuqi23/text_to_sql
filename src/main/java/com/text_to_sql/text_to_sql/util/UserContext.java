package com.text_to_sql.text_to_sql.util;

/**
 * 用户上下文工具类
 * 在线程中保存/获取当前用户ID
 */
public class UserContext {

	private static final ThreadLocal<Long> userIdHolder = new ThreadLocal<>();

	public static void setUserId(Long userId) {
		userIdHolder.set(userId);
	}

	public static Long getUserId() {
		return userIdHolder.get();
	}

	public static void clear() {
		userIdHolder.remove();
	}
}
