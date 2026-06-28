package com.text_to_sql.text_to_sql.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.text_to_sql.text_to_sql.common.agent.PythonAgent;
import com.text_to_sql.text_to_sql.common.enumeration.ErrorCodeEnum;
import com.text_to_sql.text_to_sql.common.enumeration.code.Judgment;
import com.text_to_sql.text_to_sql.common.enumeration.code.Review;
import com.text_to_sql.text_to_sql.common.result.PageResult;
import com.text_to_sql.text_to_sql.common.result.Result;
import com.text_to_sql.text_to_sql.exception.BusinessException;
import com.text_to_sql.text_to_sql.mapper.*;
import com.text_to_sql.text_to_sql.pojo.dto.SubmitDTO;
import com.text_to_sql.text_to_sql.pojo.dto.SubmitPageDTO;
import com.text_to_sql.text_to_sql.pojo.dto.SubmitUpdateDTO;
import com.text_to_sql.text_to_sql.pojo.entity.Questions;
import com.text_to_sql.text_to_sql.pojo.entity.Submit;
import com.text_to_sql.text_to_sql.pojo.vo.*;
import com.text_to_sql.text_to_sql.service.SubmitService;
import com.text_to_sql.text_to_sql.util.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
	private SolutionMapper solutionMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private PythonAgent pythonAgent;

	@Override
	public PageResult page(SubmitPageDTO submitPageDTO) {  // 前端用户页面必须传入用户ID, 也可管理端查看所有提交记录
		if (submitPageDTO.getPage() == null || submitPageDTO.getPage() <= 0) {
			submitPageDTO.setPage(1);
		}
		if (submitPageDTO.getPageSize() == null || submitPageDTO.getPageSize() <= 0) {
			submitPageDTO.setPageSize(10);
		}

		log.info("分页查询参数：{}", submitPageDTO);

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
				.review(submit.getReview())
				.build();
	}

	@Override
	public SubmitVO submit(SubmitDTO submitDTO) {
		// 判断分析
		JudgeVO judgeVO = aiJudge(submitDTO);

		// 保存提交
		Submit submit = Submit.builder()
				.questionId(submitDTO.getQuestionId())
				.userId(UserContext.getUserId())
				.content(submitDTO.getContent())
				.judgment(judgeVO.getJudgment())
				.judgeId(3L)  // 默认机器判断, id=3是系统判题
				.analysis(judgeVO.getAnalysis())
				.submitTime(submitDTO.getSubmitTime())
				.judgmentTime(LocalDateTime.now())
				.review(Review.NORMAL)
				.build();

		submitMapper.insert(submit);

		// 返回
		return SubmitVO.builder()
				.id(submit.getId())
				.judgment(judgeVO.getJudgment())
				.analysis(judgeVO.getAnalysis())
				.judgeUser(userMapper.getNameById(judgeVO.getJudgeId()))
				.judgmentTime(submit.getJudgmentTime())
				.build();
	}

	@Override
	public void review(Long submitId) {
		Submit submit = Submit.builder()
				.id(submitId)
				.review(Review.REVIEW_REQUESTED)
				.build();

		submitMapper.update(submit);
	}

	@Override
	public void update(SubmitUpdateDTO submitUpdateDTO) {
		Submit submit = submitMapper.getById(submitUpdateDTO.getId());

		submit.setJudgeId(UserContext.getUserId());
		submit.setJudgment(submitUpdateDTO.getJudgment());
		submit.setAnalysis(submitUpdateDTO.getAnalysis());
		submit.setReview(Review.NORMAL);
		submit.setJudgmentTime(LocalDateTime.now());

		submitMapper.update(submit);
	}

	private JudgeVO aiJudge(SubmitDTO submitDTO) {
		String result = pythonAgent.judgeSqlAnswer(
				questionMapper.getContentById(submitDTO.getQuestionId()),
				solutionMapper.getByQuestionId(submitDTO.getQuestionId()).get(0).getContent(),
				submitDTO.getContent()
		);
		log.info("机器判断结果：{}", result);

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(result);

			String judgmentStr = jsonNode.has("judgment") ? jsonNode.get("judgment").asText() : "";
			String analysis = jsonNode.has("analysis") ? jsonNode.get("analysis").asText() : "";

			Judgment judgment = "正确".equals(judgmentStr) ? Judgment.CORRECT : Judgment.WRONG;

			return JudgeVO.builder()
					.judgeId(3L)
					.judgment(judgment)
					.analysis(analysis)
					.build();
		} catch (Exception e) {
			log.error("解析AI判题结果失败: {}", e.getMessage(), e);
			return JudgeVO.builder()
					.judgeId(3L)
					.judgment(Judgment.WRONG)
					.analysis("解析AI结果失败: " + result)
					.build();
		}
	}
}
