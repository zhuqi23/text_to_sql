package com.text_to_sql.text_to_sql.service;

import com.text_to_sql.text_to_sql.pojo.dto.SolutionDTO;
import com.text_to_sql.text_to_sql.pojo.vo.SolutionVO;

import java.util.List;

public interface SolutionService {
	void add(SolutionDTO solutionDTO);

	void update(SolutionDTO solutionDTO);

	void delete(Long id);

	List<SolutionVO> list(Long questionId);
}
