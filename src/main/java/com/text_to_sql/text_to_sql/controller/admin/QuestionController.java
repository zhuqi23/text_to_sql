package com.text_to_sql.text_to_sql.controller.admin;

import com.text_to_sql.text_to_sql.common.result.PageResult;
import com.text_to_sql.text_to_sql.common.result.Result;
import com.text_to_sql.text_to_sql.pojo.dto.QuestionPageQueryDTO;
import com.text_to_sql.text_to_sql.service.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/question")
@Slf4j
@Api(tags = "题目管理")
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	@GetMapping("/list")
	@ApiOperation("题目列表-分页")
	public Result<PageResult> question(QuestionPageQueryDTO questionPageQueryDTO) {
		log.info("分页查询参数：{}", questionPageQueryDTO);

		return Result.success(new PageResult(10, null));
	}
}
