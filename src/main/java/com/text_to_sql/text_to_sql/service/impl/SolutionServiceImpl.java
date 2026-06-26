package com.text_to_sql.text_to_sql.service.impl;

import com.text_to_sql.text_to_sql.mapper.SolutionMapper;
import com.text_to_sql.text_to_sql.mapper.UserMapper;
import com.text_to_sql.text_to_sql.pojo.dto.SolutionDTO;
import com.text_to_sql.text_to_sql.pojo.entity.Solution;
import com.text_to_sql.text_to_sql.pojo.vo.SolutionVO;
import com.text_to_sql.text_to_sql.service.SolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SolutionServiceImpl implements SolutionService {

	@Autowired
	private SolutionMapper solutionMapper;
	@Autowired
	private UserMapper userMapper;

	@Override
	public void add(SolutionDTO solutionDTO) {
		Solution solution = Solution.builder()
				.questionId(solutionDTO.getQuestionId())
				.content(solutionDTO.getContent())
				.build();

		solutionMapper.insert(solution);
	}

	@Override
	public void update(SolutionDTO solutionDTO) {
		Solution solution = solutionMapper.getById(solutionDTO.getId());

		solution.setContent(solutionDTO.getContent());
		solutionMapper.update(solution);
	}

	@Override
	public void delete(Long id) {
		solutionMapper.delete(id);
	}

	@Override
	public List<SolutionVO> list(Long questionId) {
		List<Solution> solutions = solutionMapper.getByQuestionId(questionId);

		List<SolutionVO> solutionVOS = new ArrayList<>();
		for (Solution solution : solutions) {
			SolutionVO solutionVO = SolutionVO.builder()
					.id(solution.getId())
					.content(solution.getContent())
					.createTime(solution.getCreateTime())
					.createName(userMapper.getNameById(solution.getCreateUser()))
					.build();

			solutionVOS.add(solutionVO);
		}
		return solutionVOS;
	}


}
