package com.text_to_sql.text_to_sql.mapper;

import com.text_to_sql.text_to_sql.common.annotation.AutoFill;
import com.text_to_sql.text_to_sql.common.enumeration.OperationType;
import com.text_to_sql.text_to_sql.pojo.entity.QuestionKnowledge;
import com.text_to_sql.text_to_sql.pojo.vo.KnowledgeBatchListVO;
import com.text_to_sql.text_to_sql.pojo.vo.KnowledgeListVO;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionKnowledgeMapper {

	@Select("select count(*) from question_knowledge where knowledge_id = #{id}")
	int countByKnowledgeId(Long id);

	List<KnowledgeListVO> getKnowledgeByQuestionId(Long questionId);

	List<KnowledgeBatchListVO> getKnowledgeByQuestionIds(List<Long> questionIds);

	@AutoFill(value = OperationType.INSERT)
	void insertBatch(List<QuestionKnowledge> questionKnowledgeList);

	@Delete("delete from question_knowledge where question_id = #{id}")
	void deleteByQuestionId(Long id);
}
