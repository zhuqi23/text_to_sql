package com.text_to_sql.text_to_sql.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult {

	private long total;  // 总数
	private List records;  // 当前页面数据集合

}
