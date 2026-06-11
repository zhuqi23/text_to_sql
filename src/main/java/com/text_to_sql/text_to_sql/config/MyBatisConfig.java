package com.text_to_sql.text_to_sql.config;

import com.text_to_sql.text_to_sql.common.enumeration.code.Difficulty;
import com.text_to_sql.text_to_sql.common.enumeration.code.UserType;
import com.text_to_sql.text_to_sql.handler.CodeBasedEnumTypeHandler;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfig {

	@Bean
	public ConfigurationCustomizer mybatisConfigurationCustomizer() {
		return configuration -> {
			// 手动注册 TypeHandler
			configuration.getTypeHandlerRegistry().register(UserType.class, new CodeBasedEnumTypeHandler<>(UserType.class));
			configuration.getTypeHandlerRegistry().register(Difficulty.class, new CodeBasedEnumTypeHandler<>(Difficulty.class));
		};
	}
}
