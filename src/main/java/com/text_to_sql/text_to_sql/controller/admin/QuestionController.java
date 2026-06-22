package com.text_to_sql.text_to_sql.controller.admin;

import com.text_to_sql.text_to_sql.common.result.PageResult;
import com.text_to_sql.text_to_sql.common.result.Result;
import com.text_to_sql.text_to_sql.pojo.dto.QuestionPageDTO;
import com.text_to_sql.text_to_sql.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/question")
@Slf4j
@Tag(name = "题目管理", description = "题目相关接口")
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	@PostMapping("/list")
	@Operation(summary = "题目列表-分页", description = "分页查询题目列表")
	public Result<PageResult> question(@RequestBody QuestionPageDTO questionPageDTO) {
		log.info("分页查询参数：{}", questionPageDTO);
		return Result.success(questionService.page(questionPageDTO));
	}
}
