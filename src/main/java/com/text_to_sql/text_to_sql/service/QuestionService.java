package com.text_to_sql.text_to_sql.service;

import com.text_to_sql.text_to_sql.common.result.PageResult;
import com.text_to_sql.text_to_sql.pojo.dto.QuestionPageDTO;

public interface QuestionService {

	PageResult page(QuestionPageDTO questionPageDTO);
}
