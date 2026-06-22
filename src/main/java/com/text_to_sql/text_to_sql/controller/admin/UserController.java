package com.text_to_sql.text_to_sql.controller.admin;

import com.text_to_sql.text_to_sql.common.result.Result;
import com.text_to_sql.text_to_sql.pojo.dto.LoginDTO;
import com.text_to_sql.text_to_sql.pojo.dto.RegisterDTO;
import com.text_to_sql.text_to_sql.pojo.entity.User;
import com.text_to_sql.text_to_sql.pojo.vo.LoginVO;
import com.text_to_sql.text_to_sql.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "用户管理", description = "用户登录、注册等相关接口")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 用户登录
	 *
	 * @param loginDTO 登录参数
	 * @return 登录结果
	 */
	@PostMapping("/login")
	@Operation(summary = "用户登录", description = "用户通过账号密码登录，返回JWT令牌")
	public Result<LoginVO> login(@RequestBody LoginDTO loginDTO) {
		log.info("用户登录请求: {}", loginDTO);
		LoginVO response = userService.login(loginDTO);
		return Result.success(response);
	}

	/**
	 * 用户注册
	 *
	 * @param registerDTO 注册参数
	 * @return 注册结果
	 */
	@PostMapping("/register")
	@Operation(summary = "用户注册", description = "新用户注册账号")
	public Result<LoginVO> register(@RequestBody RegisterDTO registerDTO) {
		log.info("用户注册请求: {}", registerDTO.getAccount());
		LoginVO response = userService.register(registerDTO);
		return Result.success(response);
	}

	/**
	 * 获取用户信息
	 *
	 * @param request 请求对象
	 * @return 用户信息
	 */
	@GetMapping("/info")
	@Operation(summary = "获取用户信息", description = "根据JWT令牌获取当前登录用户信息")
	public Result<User> getUserInfo(HttpServletRequest request) {
		Long userId = (Long) request.getAttribute("userId");
		User user = userService.getUserById(userId);
		if (user != null) {
			user.setPassword(null);
		}
		return Result.success(user);
	}

	/**
	 * 退出登录
	 *
	 * @param request 请求对象
	 * @return 退出结果
	 */
	@PostMapping("/logout")
	@Operation(summary = "退出登录", description = "用户退出登录，前端需删除Token")
	public Result logout(HttpServletRequest request) {
		Long userId = (Long) request.getAttribute("userId");
		log.info("用户退出登录 - userId: {}", userId);

		// 如果需要实现 Token 黑名单机制，用到redis, 可以在这里将 Token 加入黑名单
		// String token = request.getHeader("Authorization").substring(7);
		// tokenBlacklistService.addToBlacklist(token);

		return Result.success("退出登录成功");
	}
}
