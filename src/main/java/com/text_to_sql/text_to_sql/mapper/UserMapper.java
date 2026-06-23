package com.text_to_sql.text_to_sql.mapper;

import com.github.pagehelper.Page;
import com.text_to_sql.text_to_sql.pojo.dto.UserPageDTO;
import com.text_to_sql.text_to_sql.pojo.dto.UserUpdateDTO;
import com.text_to_sql.text_to_sql.pojo.entity.User;
import com.text_to_sql.text_to_sql.pojo.vo.UserListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

	User findByAccount(@Param("account") String account);

	int insert(User user);

	User findById(@Param("id") Long id);

//	@Select("SELECT name FROM user WHERE id = #{id}")
	String getNameById(Long id);


	@Select("SELECT EXISTS(SELECT 1 FROM user WHERE account = #{account})")
	boolean isExistsByAccount(@Param("account") String account);

	@Select("SELECT EXISTS(SELECT 1 FROM user WHERE name = #{name})")
	boolean isExistsByName(String name);

	Page<UserListVO> page(UserPageDTO userPageDTO);

	void update(UserUpdateDTO userUpdateDTO);
}
