package com.text_to_sql.text_to_sql.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.text_to_sql.text_to_sql.common.enumeration.CodeBasedEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 全局注册反序列化器
 * 枚举类型转换器配置
 */
@Configuration
public class EnumDeserializerConfig {

	@Bean
	public SimpleModule codeBasedEnumModule() {
		SimpleModule module = new SimpleModule("CodeBasedEnumModule");
		module.addDeserializer(CodeBasedEnum.class, new CodeBasedEnumDeserializer());
		return module;
	}
}
