package com.text_to_sql.text_to_sql.pojo.vo;

import com.text_to_sql.text_to_sql.common.enumeration.code.Difficulty;
import com.text_to_sql.text_to_sql.common.enumeration.code.Judgment;
import com.text_to_sql.text_to_sql.common.enumeration.code.Review;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class SubmitDetailVO {

	private Long id;
	private Long questionId;
	private String title;
	private Difficulty difficulty;
	private List<KnowledgeListVO> knowledge;
	private String questionName;
	private String questionContent;
	private String submitName;
	private String submitContent;
	private LocalDateTime submitTime;
	private Judgment judgment;
	private String judgeName;
	private String analysis;
	private LocalDateTime judgmentTime;
	private Review review;
}
