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
// todo 6.23: 现在完成前端管理页面(完成对题目/知识点/的增删改查), 完成前端的新增题目/题目详情页面, 再完成提交部分
// todo 所有list改为Get, 搜索由前端完成(不一定可以, 可以就能存redis)
// todo 接下来就是连接python的ai
// todo 现在要开发完题目
