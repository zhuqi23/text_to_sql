package com.text_to_sql.text_to_sql.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MachineVO {

	private String title;
	private String content;
	private String answer;
}
