package com.text_to_sql.text_to_sql.service.impl;

import com.alibaba.druid.sql.parser.SQLParserFeature;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.text_to_sql.text_to_sql.common.annotation.AutoFill;
import com.text_to_sql.text_to_sql.common.enumeration.OperationType;
import com.text_to_sql.text_to_sql.common.result.PageResult;
import com.text_to_sql.text_to_sql.mapper.QuestionKnowledgeMapper;
import com.text_to_sql.text_to_sql.mapper.QuestionMapper;
import com.text_to_sql.text_to_sql.mapper.SolutionMapper;
import com.text_to_sql.text_to_sql.mapper.UserMapper;
import com.text_to_sql.text_to_sql.pojo.dto.MachineDTO;
import com.text_to_sql.text_to_sql.pojo.dto.QuestionDTO;
import com.text_to_sql.text_to_sql.pojo.dto.QuestionPageDTO;
import com.text_to_sql.text_to_sql.pojo.entity.Knowledge;
import com.text_to_sql.text_to_sql.pojo.entity.QuestionKnowledge;
import com.text_to_sql.text_to_sql.pojo.entity.Questions;
import com.text_to_sql.text_to_sql.pojo.entity.Solution;
import com.text_to_sql.text_to_sql.pojo.vo.*;
import com.text_to_sql.text_to_sql.service.QuestionService;
import com.text_to_sql.text_to_sql.util.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
	@Autowired
	private SolutionMapper solutionMapper;
	@Autowired
	private UserMapper userMapper;

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

	@Override
	@Transactional
	public void add(QuestionDTO questionDTO) {

		Questions questions = Questions.builder()
				.title(questionDTO.getTitle())
				.difficulty(questionDTO.getDifficulty())
				.build();

		questionMapper.insert(questions);
		questionMapper.insertDetail(questions.getId(), questionDTO.getContent());

		List<QuestionKnowledge> questionKnowledgeList = new ArrayList<>();
		for (Long knowledgeId : questionDTO.getKnowledge()) {
			QuestionKnowledge questionKnowledge = QuestionKnowledge.builder()
					.questionId(questions.getId())
					.knowledgeId(knowledgeId)
					.build();
			questionKnowledgeList.add(questionKnowledge);
		}

		questionKnowledgeMapper.insertBatch(questionKnowledgeList);

		// 若有答案一起添加
		if (questionDTO.getAnswer() != null) {
			Solution solution = Solution.builder()
					.questionId(questions.getId())
					.content(questionDTO.getAnswer())
					.build();
			solutionMapper.insert(solution);
		}
	}

	@Override
	@Transactional
	public void update(QuestionDTO questionDTO) {
		Questions questions = Questions.builder()
				.id(questionDTO.getId())
				.title(questionDTO.getTitle())
				.difficulty(questionDTO.getDifficulty())
				.build();

		questionMapper.update(questions);
		if (questionDTO.getContent() != null) {
			questionMapper.updateDetail(questions.getId(), questionDTO.getContent());
		}

		if (questionDTO.getKnowledge() != null && !questionDTO.getKnowledge().isEmpty()) {
			questionKnowledgeMapper.deleteByQuestionId(questions.getId());
			List<QuestionKnowledge> questionKnowledgeList = new ArrayList<>();
			for (Long knowledgeId : questionDTO.getKnowledge()) {
				QuestionKnowledge questionKnowledge = QuestionKnowledge.builder()
						.questionId(questions.getId())
						.knowledgeId(knowledgeId)
						.build();
				questionKnowledgeList.add(questionKnowledge);
			}
			questionKnowledgeMapper.insertBatch(questionKnowledgeList);
		}
	}

	@Override
	public QuestionDetailVO get(Long id) {
		Questions questions = questionMapper.getById(id);
		String content = questionMapper.getContentById(id);
		List<KnowledgeListVO> knowledge = questionKnowledgeMapper.getKnowledgeByQuestionId(id);

		return QuestionDetailVO.builder()
				.id(questions.getId())
				.title(questions.getTitle())
				.difficulty(questions.getDifficulty())
				.knowledge(knowledge)
				.content(content)
				.createUser(userMapper.getNameById(questions.getCreateUser()))
				.createTime(questions.getCreateTime())
				.updateUser(userMapper.getNameById(questions.getUpdateUser()))
				.updateTime(questions.getUpdateTime())
				.build();
	}

	@Override
	public MachineVO machine(MachineDTO machineDTO) {
		return aiGenerate(machineDTO);
	}

	private MachineVO aiGenerate(MachineDTO machineDTO) {
		return MachineVO.builder()
				.title("机器出题")
				.content("机器出题")
				.answer("机器出题")
				.build();
	}

}
