package com.text_to_sql.text_to_sql.mapper;

import com.text_to_sql.text_to_sql.common.annotation.AutoFill;
import com.text_to_sql.text_to_sql.common.enumeration.OperationType;
import com.text_to_sql.text_to_sql.pojo.entity.Knowledge;
import com.text_to_sql.text_to_sql.pojo.vo.KnowledgeListVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface KnowledgeMapper {
	@Select("select id, name from knowledge order by create_time")
	List<KnowledgeListVO> list();

	@Delete("delete from knowledge where id = #{id}")
	void delete(Long id);

	@Insert("INSERT INTO knowledge (name, create_user, update_user, create_time, update_time) " +
			"VALUES (#{name}, #{createUser}, #{updateUser}, #{createTime}, #{updateTime})")
	@AutoFill(value = OperationType.INSERT)
	void insert(Knowledge knowledge);

	@Select("SELECT EXISTS(SELECT 1 FROM knowledge WHERE name = #{name})")
	boolean isExistByName(String name);
}
