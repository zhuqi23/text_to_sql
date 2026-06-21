package com.text_to_sql.text_to_sql.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Solution {

	// id自增, 题目id, 题解content, 创建人id, 修改人id, 创建时间, 修改时间
	private Long id;
	private Long questionId;
	private String content;

	private LocalDateTime createTime;
	private LocalDateTime updateTime;
	private Long createUser;
	private Long updateUser;

}
