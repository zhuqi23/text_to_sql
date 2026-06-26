package com.text_to_sql.text_to_sql.service;

import com.github.pagehelper.PageInfo;
import com.text_to_sql.text_to_sql.common.result.PageResult;
import com.text_to_sql.text_to_sql.common.result.Result;
import com.text_to_sql.text_to_sql.pojo.dto.SubmitDTO;
import com.text_to_sql.text_to_sql.pojo.dto.SubmitPageDTO;
import com.text_to_sql.text_to_sql.pojo.dto.SubmitUpdateDTO;
import com.text_to_sql.text_to_sql.pojo.vo.SubmitDetailVO;
import com.text_to_sql.text_to_sql.pojo.vo.SubmitListVO;
import com.text_to_sql.text_to_sql.pojo.vo.SubmitVO;

public interface SubmitService {

	PageResult page(SubmitPageDTO submitPageDTO);

	SubmitDetailVO get(Long id);

	SubmitVO submit(SubmitDTO submitDTO);

	void review(Long id);

	void update(SubmitUpdateDTO submitUpdateDTO);
}
