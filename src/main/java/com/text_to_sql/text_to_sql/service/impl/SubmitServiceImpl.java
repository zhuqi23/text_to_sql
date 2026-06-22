package com.text_to_sql.text_to_sql.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.text_to_sql.text_to_sql.common.enumeration.ErrorCodeEnum;
import com.text_to_sql.text_to_sql.common.result.PageResult;
import com.text_to_sql.text_to_sql.exception.BusinessException;
import com.text_to_sql.text_to_sql.mapper.QuestionKnowledgeMapper;
import com.text_to_sql.text_to_sql.mapper.QuestionMapper;
import com.text_to_sql.text_to_sql.mapper.SubmitMapper;
import com.text_to_sql.text_to_sql.mapper.UserMapper;
import com.text_to_sql.text_to_sql.pojo.dto.SubmitPageDTO;
import com.text_to_sql.text_to_sql.pojo.entity.Questions;
import com.text_to_sql.text_to_sql.pojo.entity.Submit;
import com.text_to_sql.text_to_sql.pojo.vo.KnowledgeListVO;
import com.text_to_sql.text_to_sql.pojo.vo.SubmitDetailVO;
import com.text_to_sql.text_to_sql.pojo.vo.SubmitListVO;
import com.text_to_sql.text_to_sql.service.SubmitService;
import com.text_to_sql.text_to_sql.util.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jaxb.runtime.v2.runtime.reflect.ListTransducedAccessorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SubmitServiceImpl implements SubmitService {

	@Autowired
	private SubmitMapper submitMapper;
	@Autowired
	private QuestionMapper questionMapper;
	@Autowired
	private QuestionKnowledgeMapper questionKnowledgeMapper;
	@Autowired
	private UserMapper userMapper;

	@Override
	public PageResult page(SubmitPageDTO submitPageDTO) {
		if (submitPageDTO.getPage() == null || submitPageDTO.getPage() <= 0) {
			submitPageDTO.setPage(1);
		}
		if (submitPageDTO.getPageSize() == null || submitPageDTO.getPageSize() <= 0) {
			submitPageDTO.setPageSize(10);
		}

		log.info("分页查询提交列表，页码：{}，每页大小：{}", submitPageDTO.getPage(), submitPageDTO.getPageSize());

		PageHelper.startPage(submitPageDTO.getPage(), submitPageDTO.getPageSize());
		Page<SubmitListVO> page = submitMapper.page(submitPageDTO);

		log.info("查询结果：总数={}，内容={}", page.getTotal(), page.getResult());
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Override
	public SubmitDetailVO get(Long id) {
		Submit submit = submitMapper.getById(id);
		if (submit == null) {
			throw new BusinessException(ErrorCodeEnum.SUBMIT_NOT_FOUND);
		}

		Questions question = questionMapper.getById(submit.getQuestionId());
		List<KnowledgeListVO> knowledge = questionKnowledgeMapper.getKnowledgeByQuestionId(submit.getQuestionId());

		String questionCreateUserName = userMapper.getNameById(question.getCreateUser());
		String questionContent = questionMapper.getContentById(question.getId());
		String submitName = userMapper.getNameById(submit.getUserId());
		String judgeName = userMapper.getNameById(submit.getJudgeId());

		return SubmitDetailVO.builder()
				.id(submit.getId())
				.questionId(submit.getQuestionId())
				.title(question.getTitle())
				.difficulty(question.getDifficulty())
				.knowledge(knowledge)
				.questionName(questionCreateUserName)
				.questionContent(questionContent)
				.submitName(submitName)
				.submitContent(submit.getContent())
				.judgeName(judgeName)
				.judgment(submit.getJudgment())
				.analysis(submit.getAnalysis())
				.submitTime(submit.getSubmitTime())
				.judgmentTime(submit.getJudgmentTime())
				.build();
	}
}
