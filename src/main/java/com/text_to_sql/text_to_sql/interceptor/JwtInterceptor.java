package com.text_to_sql.text_to_sql.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.text_to_sql.text_to_sql.common.enumeration.ErrorCodeEnum;
import com.text_to_sql.text_to_sql.common.result.Result;
import com.text_to_sql.text_to_sql.util.JwtUtil;
import com.text_to_sql.text_to_sql.util.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Enumeration;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

	private static final String AUTH_HEADER = "Authorization";
	private static final String TOKEN_PREFIX = "Bearer ";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.info("========== JWT拦截器触发 ==========");
		log.info("请求URI: {}", request.getRequestURI());
//		log.info("请求方法: {}", request.getMethod());
		
//		Enumeration<String> headerNames = request.getHeaderNames();
//		log.info("所有请求头:");
//		while (headerNames.hasMoreElements()) {
//			String headerName = headerNames.nextElement();
//			String headerValue = request.getHeader(headerName);
//			log.info("  {} = {}", headerName, headerValue);
//		}
		
		String token = request.getHeader(AUTH_HEADER);
		log.info("Authorization Header: {}", token != null ? "存在" : "不存在");
		log.info("Authorization Value: {}", token);

		if (token == null || token.isEmpty()) {
			log.warn("❌ 缺少Token - 请求: {}", request.getRequestURI());
			sendError(response, ErrorCodeEnum.TOKEN_MISSING);
			return false;
		}

		if (token.startsWith(TOKEN_PREFIX)) {
			token = token.substring(TOKEN_PREFIX.length());
			log.info("提取JWT Token: {}...", token.substring(0, Math.min(20, token.length())));
		}

		Long userId = JwtUtil.getUserIdFromToken(token);
		log.info("解析Token - userId: {}, URI: {}", userId, request.getRequestURI());
		
		if (userId == null || JwtUtil.isTokenExpired(token)) {
			log.warn("❌ Token无效或已过期 - userId: {}", userId);
			sendError(response, ErrorCodeEnum.TOKEN_INVALID);
			return false;
		}

		UserContext.setUserId(userId);
		request.setAttribute("userId", userId);
		log.info("✅ 用户ID: {} 访问: {}", userId, request.getRequestURI());
		log.info("===================================");

		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		UserContext.clear();
		log.info("清理用户上下文 - URI: {}", request.getRequestURI());
	}

	private void sendError(HttpServletResponse response, ErrorCodeEnum errorCode) throws Exception {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json;charset=UTF-8");

		Result<Void> result = Result.error(errorCode.getCode(), errorCode.getMessage());
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(result);
		log.error("JWT验证失败 - 错误码: {}, 消息: {}", errorCode.getCode(), errorCode.getMessage());
		response.getWriter().write(json);
	}
}
