package com.text_to_sql.text_to_sql.pojo.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KnowledgeBatchListVO {

	private Long id;
	private String name;

	private Long questionId;
}
