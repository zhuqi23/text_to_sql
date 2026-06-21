package com.text_to_sql.text_to_sql.pojo.dto;

import com.text_to_sql.text_to_sql.common.enumeration.code.Difficulty;

public class QuestionPageQueryDTO {
	private Integer page;
	private Integer pageSize;

	private String title;
	private Difficulty difficulty;
	private String name;  // 知识点名称

}
