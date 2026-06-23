package com.text_to_sql.text_to_sql.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.text_to_sql.text_to_sql.common.enumeration.ErrorCodeEnum;
import com.text_to_sql.text_to_sql.common.enumeration.code.UserStatus;
import com.text_to_sql.text_to_sql.common.enumeration.code.UserType;
import com.text_to_sql.text_to_sql.common.result.PageResult;
import com.text_to_sql.text_to_sql.exception.BusinessException;
import com.text_to_sql.text_to_sql.mapper.UserMapper;
import com.text_to_sql.text_to_sql.pojo.dto.LoginDTO;
import com.text_to_sql.text_to_sql.pojo.dto.RegisterDTO;
import com.text_to_sql.text_to_sql.pojo.dto.UserPageDTO;
import com.text_to_sql.text_to_sql.pojo.dto.UserUpdateDTO;
import com.text_to_sql.text_to_sql.pojo.entity.User;
import com.text_to_sql.text_to_sql.pojo.vo.LoginVO;
import com.text_to_sql.text_to_sql.pojo.vo.UserListVO;
import com.text_to_sql.text_to_sql.service.UserService;
import com.text_to_sql.text_to_sql.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public LoginVO login(LoginDTO loginDTO) {
		User user = userMapper.findByAccount(loginDTO.getAccount());

		if (user == null) {
			throw new BusinessException(ErrorCodeEnum.USER_NOT_FOUND);
		}

		if (user.getStatus().equals(UserStatus.BANNED)) {
			throw new BusinessException(ErrorCodeEnum.USER_BLACKLISTED);
		}

		// 密码加密, 数据库中管理员的密码是明文, 普通用户的密码是加密的
		String password = loginDTO.getPassword();
		if (user.getType().equals(UserType.USER)) {  // 判断查出来的用户是否是管理员，不是就得判断加密密码
			password = encryptPassword(password);
		}
		if (!user.getPassword().equals(password)) {
			log.warn("密码错误, 密码是: " + password);
			throw new BusinessException(ErrorCodeEnum.INVALID_PASSWORD);
		}

		String token = JwtUtil.generateToken(user.getId(), user.getAccount(), user.getType().getCode());

		return LoginVO.builder()
				.id(user.getId())
				.token(token)
				.account(user.getAccount())
				.name(user.getName())
				.type(user.getType())
				.build();
	}

	@Override
	@Transactional
	public LoginVO register(RegisterDTO registerDTO) {
		if (userMapper.isExistsByAccount(registerDTO.getAccount())) {
			throw new BusinessException(ErrorCodeEnum.ACCOUNT_ALREADY_EXISTS);
		}
		if (userMapper.isExistsByName(registerDTO.getName())) {
			throw new BusinessException(ErrorCodeEnum.USERNAME_ALREADY_EXISTS);
		}

		User newUser = User.builder()
				.account(registerDTO.getAccount())
				.password(encryptPassword(registerDTO.getPassword()))
				.name(registerDTO.getName())
				.type(UserType.USER)
				.status(UserStatus.NORMAL)
				.createTime(LocalDateTime.now())
				.build();

		int result = userMapper.insert(newUser);
		if (result <= 0) {
			throw new BusinessException(ErrorCodeEnum.REGISTER_FAILED);
		}

		String token = JwtUtil.generateToken(newUser.getId(), newUser.getAccount(), newUser.getType().getCode());

		return LoginVO.builder()
				.id(newUser.getId())
				.token(token)
				.account(newUser.getAccount())
				.name(newUser.getName())
				.type(newUser.getType())
				.build();
	}

	@Override
	public User getUserById(Long userId) {
		return userMapper.findById(userId);
	}

	@Override
	public PageResult list(UserPageDTO userPageDTO) {
		if (userPageDTO.getPage() == null || userPageDTO.getPage() <= 0) {
			userPageDTO.setPage(1);
		}
		if (userPageDTO.getPageSize() == null || userPageDTO.getPageSize() <= 0) {
			userPageDTO.setPageSize(10);
		}

		PageHelper.startPage(userPageDTO.getPage(), userPageDTO.getPageSize());
		Page<UserListVO> page = userMapper.page(userPageDTO);
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Override
	public void update(UserUpdateDTO userUpdateDTO) {
		if (userUpdateDTO.getPassword() != null) {
			userUpdateDTO.setPassword(encryptPassword(userUpdateDTO.getPassword()));
		}
		userMapper.update(userUpdateDTO);
	}

	// 密码加密
	private String encryptPassword(String password) {
		return DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
	}
}
