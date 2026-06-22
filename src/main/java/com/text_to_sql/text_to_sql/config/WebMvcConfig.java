package com.text_to_sql.text_to_sql.config;

import com.text_to_sql.text_to_sql.interceptor.JwtInterceptor;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

/**
 * 配置Web层配置
 * 	swagger的knife4j配置
 * 	静态资源映射配置
 * @author: 18040
 * @date: 2023/4/7 21:05
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired
	private JwtInterceptor jwtInterceptor;

	@Bean
	public OpenAPI customOpenAPI() {
		log.info("开始创建Knife4j文档配置...");
		return new OpenAPI()
				.info(new Info()
						.title("TextToSQL API文档")
						.version("1.0")
						.description("文本转SQL系统接口文档"))
				.components(new io.swagger.v3.oas.models.Components()
						.addSecuritySchemes("Bearer Authentication",
								new SecurityScheme()
										.type(SecurityScheme.Type.HTTP)
										.scheme("bearer")
										.bearerFormat("JWT")
										.description("JWT令牌认证")))
				.security(Collections.singletonList(
						new SecurityRequirement().addList("Bearer Authentication")));
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		log.info("开始进行静态资源映射...");
		registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		log.info("注册JWT拦截器...");
		registry.addInterceptor(jwtInterceptor)
				.addPathPatterns("/admin/**")
				.excludePathPatterns("/admin/user/login", "/admin/user/register", "/admin/*/list");
	}
}
