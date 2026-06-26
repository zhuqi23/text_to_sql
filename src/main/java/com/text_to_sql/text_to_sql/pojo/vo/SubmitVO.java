package com.text_to_sql.text_to_sql.pojo.vo;

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
public class SubmitVO {

	private Long id;  // 提交ID
	private Judgment judgment;
	private String analysis;

	private String judgeUser;
	private LocalDateTime judgmentTime;
}
