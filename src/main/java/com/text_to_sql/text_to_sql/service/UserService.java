package com.text_to_sql.text_to_sql.service;

import com.text_to_sql.text_to_sql.pojo.dto.LoginDTO;
import com.text_to_sql.text_to_sql.pojo.dto.RegisterDTO;
import com.text_to_sql.text_to_sql.pojo.entity.User;
import com.text_to_sql.text_to_sql.pojo.vo.LoginVO;

public interface UserService {

	LoginVO login(LoginDTO loginRequest);

	LoginVO register(RegisterDTO registerRequest);

	User getUserById(Long userId);
}
