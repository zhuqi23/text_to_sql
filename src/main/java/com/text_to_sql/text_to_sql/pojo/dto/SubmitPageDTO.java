package com.text_to_sql.text_to_sql.pojo.dto;

import com.text_to_sql.text_to_sql.common.enumeration.code.Judgment;
import lombok.Data;

@Data
public class SubmitPageDTO {

	private Integer page;
	private Integer pageSize;

	private Long questionId;
	private Long userId;
	private Judgment judgment;
	private String title;
}
