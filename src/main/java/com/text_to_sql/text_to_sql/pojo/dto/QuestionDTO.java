package com.text_to_sql.text_to_sql.pojo.dto;

import com.text_to_sql.text_to_sql.common.enumeration.code.Difficulty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionDTO {

	private Long id;
	private String title;
	private Difficulty difficulty;
	private List<Long> knowledge;
	private String content;

	private String answer;
}
