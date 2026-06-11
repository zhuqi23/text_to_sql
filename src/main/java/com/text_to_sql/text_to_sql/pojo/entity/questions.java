package com.text_to_sql.text_to_sql.pojo.entity;

import com.text_to_sql.text_to_sql.common.enumeration.code.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class questions {

	// id自增, 题目名称title, 难度(0~3其他简单中等困难)difficulty, 创建人id, 创建时间, 修改时间, 修改人id
	private Long id;
	private String title;
	private Difficulty difficulty;

	private LocalDateTime createTime;
	private LocalDateTime updateTime;
	private Long createUser;
	private Long updateUser;

}
