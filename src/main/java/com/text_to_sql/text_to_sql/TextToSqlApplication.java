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
// 6.27: 改list, 前端, 连接python; 6.28: 开发ai; 6.29: 完善,完成ppt 最后转redis
// todo 后端基本完成(没具体测试, 但应该没问题), 除了调用python, 转redis,
//  将所有list/page改为Get, 搜索由前端完成(不一定可以, 可以就能存redis)
// todo 前端还差: 题目详情页面, 新增/修改题目页面, 知识点的修改, 提交记录新增review, ...
// todo 总: 后端, 前端, python连接, ai, redis
