package com.text_to_sql.text_to_sql.service;

import com.text_to_sql.text_to_sql.common.result.PageResult;
import com.text_to_sql.text_to_sql.pojo.dto.MachineDTO;
import com.text_to_sql.text_to_sql.pojo.dto.QuestionDTO;
import com.text_to_sql.text_to_sql.pojo.dto.QuestionPageDTO;
import com.text_to_sql.text_to_sql.pojo.vo.MachineVO;
import com.text_to_sql.text_to_sql.pojo.vo.QuestionDetailVO;

public interface QuestionService {

	PageResult page(QuestionPageDTO questionPageDTO);

	void add(QuestionDTO questionDTO);

	void update(QuestionDTO questionDTO);

	QuestionDetailVO get(Long id);

	MachineVO machine(MachineDTO machineDTO);

	MachineVO analysis(MachineDTO machineDTO);
}
