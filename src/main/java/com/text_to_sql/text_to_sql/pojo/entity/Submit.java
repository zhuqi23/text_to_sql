package com.text_to_sql.text_to_sql.pojo.entity;

import com.text_to_sql.text_to_sql.common.enumeration.code.Judgment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Submit {

	private Long id;
	private Long questionId;
	private Long userId;
	private String content;
	private Judgment judgment;
	private Long judgeId;
	private String analysis;
	private LocalDateTime submitTime;
	private LocalDateTime judgmentTime;
}