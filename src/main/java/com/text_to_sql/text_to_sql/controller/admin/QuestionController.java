package com.text_to_sql.text_to_sql.controller.admin;

import com.text_to_sql.text_to_sql.common.result.PageResult;
import com.text_to_sql.text_to_sql.common.result.Result;
import com.text_to_sql.text_to_sql.pojo.dto.MachineDTO;
import com.text_to_sql.text_to_sql.pojo.dto.QuestionDTO;
import com.text_to_sql.text_to_sql.pojo.dto.QuestionPageDTO;
import com.text_to_sql.text_to_sql.pojo.vo.MachineVO;
import com.text_to_sql.text_to_sql.pojo.vo.QuestionDetailVO;
import com.text_to_sql.text_to_sql.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
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

	/**
	 * 分页查询题目列表
	 * @param questionPageDTO
	 * @return
	 */
	@PostMapping("/list")
	@Operation(summary = "题目列表-分页", description = "分页查询题目列表")
	public Result<PageResult> question(@RequestBody QuestionPageDTO questionPageDTO) {
		log.info("分页查询参数：{}", questionPageDTO);
		return Result.success(questionService.page(questionPageDTO));
	}

	/**
	 * 添加题目
	 * @param questionDTO
	 * @return
	 */
	@PostMapping("/add")
	@Operation(summary = "添加题目", description = "添加题目")
	public Result add(@RequestBody QuestionDTO questionDTO) {
		log.info("添加题目，参数：{}", questionDTO);
		questionService.add(questionDTO);
		return Result.success();
	}

	/**
	 * 修改题目
	 * @param questionDTO
	 * @return
	 */
	@PostMapping("/update")
	@Operation(summary = "修改题目", description = "修改题目")
	public Result update(@RequestBody QuestionDTO questionDTO) {
		log.info("修改题目，参数：{}", questionDTO);
		questionService.update(questionDTO);
		return Result.success();
	}

	/**
	 * 获取题目详情
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	@Operation(summary = "题目详情", description = "获取题目详情")
	public Result<QuestionDetailVO> detail(@PathVariable Long id) {
		log.info("获取题目详情, id {}", id);
		return Result.success(questionService.get(id));
	}

	/**
	 * 机器出题
	 * 不添加到数据库, 只是返回给前端, 等修改好后再有POST /add添加
	 * @param
	 * @return
	 */
	@PostMapping("/machine")
	@Operation(summary = "机器出题", description = "机器出题")
	public Result<MachineVO> machine(@RequestBody MachineDTO machineDTO) {
		log.info("机器出题，参数：{}", machineDTO);
		return Result.success(questionService.machine(machineDTO));
	}

	/**
	 * 智能答题 answer
	 * 不添加到数据库, 只是返回给前端
	 * 传来title, knowledge, content
	 */
	@PostMapping("/answer")
	@Operation(summary = "智能答题", description = "智能答题")
	public Result<MachineVO> answer(@RequestBody MachineDTO machineDTO) {
		log.info("智能答题，参数：{}", machineDTO);
		return Result.success(questionService.answer(machineDTO));
	}
}
