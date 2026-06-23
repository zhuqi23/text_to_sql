package com.text_to_sql.text_to_sql.service;

import com.text_to_sql.text_to_sql.common.result.PageResult;
import com.text_to_sql.text_to_sql.pojo.dto.LoginDTO;
import com.text_to_sql.text_to_sql.pojo.dto.RegisterDTO;
import com.text_to_sql.text_to_sql.pojo.dto.UserPageDTO;
import com.text_to_sql.text_to_sql.pojo.dto.UserUpdateDTO;
import com.text_to_sql.text_to_sql.pojo.entity.User;
import com.text_to_sql.text_to_sql.pojo.vo.LoginVO;

public interface UserService {

	LoginVO login(LoginDTO loginRequest);

	LoginVO register(RegisterDTO registerRequest);

	User getUserById(Long userId);

	PageResult list(UserPageDTO userPageDTO);

	void update(UserUpdateDTO userUpdateDTO);
}
