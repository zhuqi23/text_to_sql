package com.text_to_sql.text_to_sql.pojo.dto;

import com.text_to_sql.text_to_sql.common.enumeration.code.Difficulty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MachineDTO {

	private Difficulty difficulty;
	private List<Long> knowledge;
	private String other;  // 用户要的出题信息
	// todo 在python的ai中要尽量出不一样, 相似度不高的题目, 将question表可作为ai的知识库, 让他学但出不一样的题
}
