package com.text_to_sql.text_to_sql.pojo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SolutionDTO {

	private Long id;
	private Long questionId;
	private String content;
}
