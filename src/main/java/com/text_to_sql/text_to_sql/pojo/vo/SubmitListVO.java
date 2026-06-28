package com.text_to_sql.text_to_sql.pojo.vo;

import com.text_to_sql.text_to_sql.common.enumeration.code.Judgment;
import com.text_to_sql.text_to_sql.common.enumeration.code.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubmitListVO {

	private LocalDateTime submitTime;
	private Long id;
	private String questionId;
	private String title;
	private String name;  // 提交人name
	private Judgment judgment;
	private LocalDateTime judgmentTime;
	private String judgeName;  // 判断人name
	private Review review;
}
