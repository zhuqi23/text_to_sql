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
public class Knowledge {  // 知识点

	private Long id;

	// 知识点名称
	private String name;

	// 创建人id, 修改人id, 创建时间, 修改时间
	private Long createUser;
	private Long updateUser;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;
}
