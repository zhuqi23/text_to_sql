package com.text_to_sql.text_to_sql.controller.admin;

import com.text_to_sql.text_to_sql.common.result.Result;
import com.text_to_sql.text_to_sql.pojo.dto.SolutionDTO;
import com.text_to_sql.text_to_sql.pojo.vo.SolutionVO;
import com.text_to_sql.text_to_sql.service.SolutionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/solution")
@Slf4j
@Tag(name = "题解管理", description = "题解相关接口")
public class SolutionController {

	@Autowired
	private SolutionService solutionService;

	/**
	 * 添加题解
	 * @param solutionDTO
	 * @return
	 */
	@PostMapping("/add")
	@Operation(summary = "添加题解", description = "新增题解")
	public Result add(@RequestBody SolutionDTO solutionDTO) {
		log.info("添加题解，参数：{}", solutionDTO);
		solutionService.add(solutionDTO);
		return Result.success();
	}

	/**
	 * 修改题解
	 * @param solutionDTO
	 * @return
	 */
	@PostMapping("/update")
	@Operation(summary = "修改题解", description = "修改题解")
	public Result update(@RequestBody SolutionDTO solutionDTO) {
		log.info("修改题解，参数：{}", solutionDTO);
		solutionService.update(solutionDTO);
		return Result.success();
	}

	/**
	 * 删除题解
	 * @return
	 */
	@DeleteMapping("/delete")
	@Operation(summary = "删除题解", description = "删除题解")
	public Result delete(Long id) {
		log.info("删除题解，参数：{}", id);
		solutionService.delete(id);
		return Result.success();
	}

	/**
	 * 查询题解列表
	 * @return
	 */
	@GetMapping("/list")
	@Operation(summary = "查询题解列表", description = "查询题解列表")
	public Result<List<SolutionVO>> list(@RequestParam("question_id") Long questionId) {
		log.info("查询题解列表，参数：{}", questionId);
		return Result.success(solutionService.list(questionId));
	}
}
