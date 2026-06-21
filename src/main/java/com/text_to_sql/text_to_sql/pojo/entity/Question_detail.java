package com.text_to_sql.text_to_sql.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Question_detail {

	// id自增, 题目question_id, 题目内容content
	private Long id;
	private Long questionId;
	private String content;

}
