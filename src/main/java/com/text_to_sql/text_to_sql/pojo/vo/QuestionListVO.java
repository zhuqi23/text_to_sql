package com.text_to_sql.text_to_sql.pojo.vo;

import com.text_to_sql.text_to_sql.common.enumeration.code.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionListVO {

	private Long id;
	private String title;
	private Difficulty difficulty;
	private List<KnowledgeListVO> knowledge;
}
