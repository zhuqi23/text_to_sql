package com.text_to_sql.text_to_sql.mapper;

import com.github.pagehelper.Page;
import com.text_to_sql.text_to_sql.pojo.dto.QuestionPageDTO;
import com.text_to_sql.text_to_sql.pojo.entity.Questions;
import com.text_to_sql.text_to_sql.pojo.vo.QuestionListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
}
