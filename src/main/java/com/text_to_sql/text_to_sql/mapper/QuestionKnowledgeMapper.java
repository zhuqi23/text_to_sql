package com.text_to_sql.text_to_sql.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface QuestionKnowledgeMapper {

	@Select("select count(*) from question_knowledge where knowledge_id = #{id}")
	int countByKnowledgeId(Long id);
}
