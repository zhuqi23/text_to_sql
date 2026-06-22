package com.text_to_sql.text_to_sql.service;

import com.github.pagehelper.PageInfo;
import com.text_to_sql.text_to_sql.common.result.PageResult;
import com.text_to_sql.text_to_sql.pojo.dto.SubmitPageDTO;
import com.text_to_sql.text_to_sql.pojo.vo.SubmitDetailVO;
import com.text_to_sql.text_to_sql.pojo.vo.SubmitListVO;

public interface SubmitService {

	PageResult page(SubmitPageDTO submitPageDTO);

	SubmitDetailVO get(Long id);
}
