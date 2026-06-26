package com.text_to_sql.text_to_sql.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SolutionVO {

	private Long id;
	private String content;
	private String createName;
	private LocalDateTime createTime;
}
