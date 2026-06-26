package com.text_to_sql.text_to_sql.mapper;

import com.text_to_sql.text_to_sql.common.annotation.AutoFill;
import com.text_to_sql.text_to_sql.common.enumeration.OperationType;
import com.text_to_sql.text_to_sql.pojo.entity.Solution;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SolutionMapper {

	@Insert("INSERT INTO solution (question_id, content, create_user, create_time, update_user, update_time) " +
			"VALUES (#{questionId}, #{content}, #{createUser}, #{createTime}, #{updateUser}, #{updateTime})")
	@AutoFill(value = OperationType.INSERT)
	void insert(Solution solution);

	@Select("SELECT * FROM solution WHERE id = #{id}")
	Solution getById(Long id);

	@AutoFill(value = OperationType.UPDATE)
	void update(Solution solution);

	@Delete("DELETE FROM solution WHERE id = #{id}")
	void delete(Long id);

	@Select("SELECT * FROM solution WHERE question_id = #{questionId}")
	List<Solution> getByQuestionId(Long questionId);
}
