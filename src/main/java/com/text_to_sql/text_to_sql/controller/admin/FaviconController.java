package com.text_to_sql.text_to_sql.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class FaviconController {

	@GetMapping("/favicon.ico")
	public ResponseEntity<Void> favicon() {
		log.info("请求favicon.ico");
		return ResponseEntity.noContent().build();
	}
}
