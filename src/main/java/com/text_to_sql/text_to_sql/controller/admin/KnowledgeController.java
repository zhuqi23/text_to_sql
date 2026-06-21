package com.text_to_sql.text_to_sql.controller.admin;

import com.text_to_sql.text_to_sql.common.result.Result;
import com.text_to_sql.text_to_sql.pojo.vo.KnowledgeListVO;
import com.text_to_sql.text_to_sql.service.KnowledgeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/knowledge")
@Tag(name = "知识点管理", description = "知识点相关接口")
@Slf4j
public class KnowledgeController {

	@Autowired
	private KnowledgeService knowledgeService;

	/**
	 * 查询知识点列表
	 * @param
	 * @return
	 */
	@GetMapping("/list")
	@Operation(summary = "知识点列表", description = "查询知识点列表")
	public Result<List<KnowledgeListVO>> list() {
		log.info("知识点查询所有");
		List<KnowledgeListVO> list = knowledgeService.list();
		return Result.success(list);
	}

	/**
	 * 删除知识点
	 * @param id
	 * @return
	 */
	@DeleteMapping("/delete")
	@Operation(summary = "删除知识点", description = "根据ID删除知识点")
	public Result delete(Long id) {
		log.info("删除知识点，id：{}", id);
		knowledgeService.delete(id);
		return Result.success();
	}

	/**
	 * 添加知识点
	 * @param name 知识点名称
	 * @return
	 */
	@PostMapping("/add")
	@Operation(summary = "添加知识点", description = "新增知识点")
	public Result add(@RequestParam String name) {
		log.info("添加知识点，名称：{}", name);
		knowledgeService.add(name);
		return Result.success();
	}
}
