package com.text_to_sql.text_to_sql.service;

import com.text_to_sql.text_to_sql.pojo.vo.KnowledgeListVO;

import java.util.List;

public interface KnowledgeService {
	List<KnowledgeListVO> list();

	void delete(Long id);

	void add(String name);
}
