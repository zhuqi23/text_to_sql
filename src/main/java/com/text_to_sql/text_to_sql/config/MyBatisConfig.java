package com.text_to_sql.text_to_sql.config;

import com.text_to_sql.text_to_sql.common.enumeration.code.Difficulty;
import com.text_to_sql.text_to_sql.common.enumeration.code.Judgment;
import com.text_to_sql.text_to_sql.common.enumeration.code.Review;
import com.text_to_sql.text_to_sql.common.enumeration.code.UserType;
import com.text_to_sql.text_to_sql.handler.DifficultyHandler;
import com.text_to_sql.text_to_sql.handler.JudgmentHandler;
import com.text_to_sql.text_to_sql.handler.ReviewHandler;
import com.text_to_sql.text_to_sql.handler.UserTypeHandler;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfig {

	@Bean
	public ConfigurationCustomizer mybatisConfigurationCustomizer() {
		return configuration -> {
			configuration.getTypeHandlerRegistry().register(UserType.class, new UserTypeHandler());
			configuration.getTypeHandlerRegistry().register(Difficulty.class, new DifficultyHandler());
			configuration.getTypeHandlerRegistry().register(Judgment.class, new JudgmentHandler());
			configuration.getTypeHandlerRegistry().register(Review.class, new ReviewHandler());
		};
	}
}
