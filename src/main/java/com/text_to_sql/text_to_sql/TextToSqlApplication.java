package com.text_to_sql.text_to_sql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class TextToSqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(TextToSqlApplication.class, args);
	}

}
// todo 总: ai, ppt; redis，该list为GET(搜索由前端完成)
// todo 小细节： 前端：设计ai的部分运行时不能操作，用户尝试过的提交题目要不能重复（通过后不显示），题目详情中的提交记录更详细点
