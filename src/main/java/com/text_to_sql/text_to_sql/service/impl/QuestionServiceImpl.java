package com.text_to_sql.text_to_sql.service.impl;

import com.alibaba.druid.sql.parser.SQLParserFeature;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.text_to_sql.text_to_sql.common.result.PageResult;
import com.text_to_sql.text_to_sql.mapper.QuestionKnowledgeMapper;
import com.text_to_sql.text_to_sql.mapper.QuestionMapper;
import com.text_to_sql.text_to_sql.pojo.dto.QuestionPageDTO;
import com.text_to_sql.text_to_sql.pojo.vo.KnowledgeBatchListVO;
import com.text_to_sql.text_to_sql.pojo.vo.KnowledgeListVO;
import com.text_to_sql.text_to_sql.pojo.vo.QuestionListVO;
import com.text_to_sql.text_to_sql.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionMapper questionMapper;
	@Autowired
	private QuestionKnowledgeMapper questionKnowledgeMapper;

	@Override
	public PageResult page(QuestionPageDTO questionPageDTO) {
		if (questionPageDTO.getPage() == null || questionPageDTO.getPage() <= 0) {
			questionPageDTO.setPage(1);
		}
		if (questionPageDTO.getPageSize() == null || questionPageDTO.getPageSize() <= 0) {
			questionPageDTO.setPageSize(10);
		}

		// 分页查询题目(没有查询对应知识点)
		PageHelper.startPage(questionPageDTO.getPage(), questionPageDTO.getPageSize());
		Page<QuestionListVO> page = questionMapper.page(questionPageDTO);

		if (page.isEmpty()) {
			return new PageResult(0, page.getResult());
		}

		// 提取所有题目id
		List<Long> questionIds = page.getResult().stream()
				.map(QuestionListVO::getId)
				.toList();

		// 批量查询对应知识点
		List<KnowledgeBatchListVO> knowledgeBatchListVO = questionKnowledgeMapper.getKnowledgeByQuestionIds(questionIds);

		// 组装
		Map<Long, List<KnowledgeListVO>> group = knowledgeBatchListVO.stream()
				.collect(Collectors.groupingBy(
						KnowledgeBatchListVO::getQuestionId,
						Collectors.mapping(kb -> KnowledgeListVO.builder()
								.id(kb.getId())
								.name(kb.getName())
								.build(),
								Collectors.toList())
				));

		for (QuestionListVO question : page.getResult()) {
			List<KnowledgeListVO> knowledge = group.getOrDefault(question.getId(), List.of());
			question.setKnowledge(knowledge);
		}

		log.info("查询完成，总数：{}", page.getTotal());
		return new PageResult(page.getTotal(), page.getResult());

	}
}
