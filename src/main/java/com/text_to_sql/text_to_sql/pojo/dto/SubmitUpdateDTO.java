package com.text_to_sql.text_to_sql.pojo.dto;

import com.text_to_sql.text_to_sql.common.enumeration.code.Judgment;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubmitUpdateDTO {

	private Long id;
	private Judgment judgment;
	private String analysis;
}
