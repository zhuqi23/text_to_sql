package com.text_to_sql.text_to_sql.pojo.dto;

import com.text_to_sql.text_to_sql.common.enumeration.code.Difficulty;
import lombok.Data;

import java.util.List;

@Data
public class QuestionPageDTO {
	private Integer page;
	private Integer pageSize;

	private String title;
	private Difficulty difficulty;
	private List<Long> knowledge;  // 知识点名称

}
