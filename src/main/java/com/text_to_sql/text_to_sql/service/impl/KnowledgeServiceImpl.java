package com.text_to_sql.text_to_sql.service.impl;

import com.text_to_sql.text_to_sql.common.enumeration.ErrorCodeEnum;
import com.text_to_sql.text_to_sql.exception.BusinessException;
import com.text_to_sql.text_to_sql.mapper.KnowledgeMapper;
import com.text_to_sql.text_to_sql.mapper.QuestionKnowledgeMapper;
import com.text_to_sql.text_to_sql.pojo.entity.Knowledge;
import com.text_to_sql.text_to_sql.pojo.vo.KnowledgeListVO;
import com.text_to_sql.text_to_sql.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class KnowledgeServiceImpl implements KnowledgeService {
	
	@Autowired
	private KnowledgeMapper knowledgeMapper;
	@Autowired
	private QuestionKnowledgeMapper questionKnowledgeMapper;

	/**
	 * 查询知识点列表
	 * @param
	 * @return
	 */
	@Override
	public List<KnowledgeListVO> list() {
		return knowledgeMapper.list();
	}

	/**
	 * 删除知识点
	 * 有关联时不能删
	 * @param id
	 */
	@Override
	public void delete(Long id) {
		int count = questionKnowledgeMapper.countByKnowledgeId(id);
		if (count > 0) {
			throw new BusinessException(ErrorCodeEnum.KNOWLEDGE_HAS_QUESTIONS, count);
		} else {
			knowledgeMapper.delete(id);
		}
	}

	/**
	 * 添加知识点
	 * @param name 知识点名称
	 */
	@Override
	public void add(String name) {
		// 参数校验
		if (name == null || name.trim().isEmpty()) {
			throw new BusinessException(ErrorCodeEnum.PARAM_ERROR);
		}
		
		if (knowledgeMapper.isExistByName(name)) {
			throw new BusinessException(ErrorCodeEnum.KNOWLEDGE_EXISTS);
		}
		Knowledge knowledge = Knowledge.builder()
				.name(name)
				.build();
		knowledgeMapper.insert(knowledge);
	}
}
