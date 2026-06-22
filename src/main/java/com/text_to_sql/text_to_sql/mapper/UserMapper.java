package com.text_to_sql.text_to_sql.mapper;

import com.text_to_sql.text_to_sql.pojo.entity.User;
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
}
