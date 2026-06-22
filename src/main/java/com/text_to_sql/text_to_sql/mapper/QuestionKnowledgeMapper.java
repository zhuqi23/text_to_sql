package com.text_to_sql.text_to_sql.mapper;

import com.text_to_sql.text_to_sql.pojo.vo.KnowledgeBatchListVO;
import com.text_to_sql.text_to_sql.pojo.vo.KnowledgeListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionKnowledgeMapper {

	@Select("select count(*) from question_knowledge where knowledge_id = #{id}")
	int countByKnowledgeId(Long id);

	List<KnowledgeListVO> getKnowledgeByQuestionId(Long questionId);

	List<KnowledgeBatchListVO> getKnowledgeByQuestionIds(List<Long> questionIds);
}
