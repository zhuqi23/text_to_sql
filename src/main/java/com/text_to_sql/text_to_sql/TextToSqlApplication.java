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
// todo 现在要开发完题目
