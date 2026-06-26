package com.text_to_sql.text_to_sql.mapper;

import com.github.pagehelper.Page;
import com.text_to_sql.text_to_sql.common.annotation.AutoFill;
import com.text_to_sql.text_to_sql.common.enumeration.OperationType;
import com.text_to_sql.text_to_sql.pojo.dto.QuestionPageDTO;
import com.text_to_sql.text_to_sql.pojo.entity.Questions;
import com.text_to_sql.text_to_sql.pojo.vo.QuestionListVO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface QuestionMapper {

	@Select("SELECT * FROM questions WHERE id = #{questionId}")
	Questions getById(Long questionId);

	/**
	 * 从 question_detail 表中获取问题内容
	 * @param questionId
	 * @return
	 */
	@Select("SELECT content FROM question_detail WHERE question_id = #{questionId}")
	String getContentById(Long questionId);

	Page<QuestionListVO> page(QuestionPageDTO questionPageQueryDTO);



	@Insert("INSERT INTO questions (title, difficulty, create_time, update_time, create_user, update_user) VALUES (#{title}, #{difficulty}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	@AutoFill(value = OperationType.INSERT)
	void insert(Questions questions);

	@Insert("INSERT INTO question_detail (question_id, content) VALUES (#{questionId}, #{content})")
	void insertDetail(Long questionId, String content);

	@AutoFill(value = OperationType.UPDATE)
	void update(Questions questions);

	@Update("UPDATE question_detail SET content = #{content} WHERE question_id = #{id}")
	void updateDetail(Long id, String content);
}
