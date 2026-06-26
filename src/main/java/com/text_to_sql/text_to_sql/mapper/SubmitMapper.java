package com.text_to_sql.text_to_sql.mapper;

import com.github.pagehelper.Page;
import com.text_to_sql.text_to_sql.pojo.dto.SubmitPageDTO;
import com.text_to_sql.text_to_sql.pojo.entity.Submit;
import com.text_to_sql.text_to_sql.pojo.vo.SubmitDetailVO;
import com.text_to_sql.text_to_sql.pojo.vo.SubmitListVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SubmitMapper {

	Page<SubmitListVO> page(SubmitPageDTO submitPageDTO);

	@Select("SELECT * FROM submit WHERE id = #{id}")
	Submit getById(Long id);

	@Insert("INSERT INTO submit (question_id, user_id, content, judgment, judge_id, analysis, submit_time, judgment_time, review) VALUES (#{questionId}, #{userId}, #{content}, #{judgment}, #{judgeId}, #{analysis}, #{submitTime}, #{judgmentTime}, #{review})")
	void insert(Submit submit);

	void update(Submit submit);
}
