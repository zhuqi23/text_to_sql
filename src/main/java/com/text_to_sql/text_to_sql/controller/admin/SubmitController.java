package com.text_to_sql.text_to_sql.controller.admin;

import com.github.pagehelper.PageInfo;
import com.text_to_sql.text_to_sql.common.result.PageResult;
import com.text_to_sql.text_to_sql.common.result.Result;
import com.text_to_sql.text_to_sql.pojo.dto.SubmitPageDTO;
import com.text_to_sql.text_to_sql.pojo.vo.SubmitDetailVO;
import com.text_to_sql.text_to_sql.pojo.vo.SubmitListVO;
import com.text_to_sql.text_to_sql.service.SubmitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/submit")
@Tag(name = "提交管理", description = "提交相关接口")
@Slf4j
public class SubmitController {

	@Autowired
	private SubmitService submitService;

	/**
	 * 提交列表-分页
	 *
	 * @param submitPageDTO 分页参数
	 * @return 提交列表
	 */
	@PostMapping("/list")
	@Operation(summary = "提交列表-分页", description = "分页查询提交列表")
	public Result<PageResult> list(@RequestBody SubmitPageDTO submitPageDTO) {
		log.info("获取提交列表，参数：{}", submitPageDTO);
		return Result.success(submitService.page(submitPageDTO));
	}

	/**
	 * 提交详情
	 *
	 * @param id 提交ID
	 * @return 提交详情
	 */
	@GetMapping("/{id}")
	@Operation(summary = "提交详情", description = "根据ID查询提交详情")
	public Result<SubmitDetailVO> get(@PathVariable Long id) {
		log.info("获取提交详情, id {}", id);
		return Result.success(submitService.get(id));
	}

	/**
	 * 提交判断
	 *
	 * @param
	 * @return 提交列表
	 */
	@PostMapping("/submit")
	@Operation(summary = "提交判断", description = "提交解答分析后返回")
	public Result submit() {
		return Result.success();
	}
}
