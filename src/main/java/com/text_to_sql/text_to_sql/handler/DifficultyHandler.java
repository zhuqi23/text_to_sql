package com.text_to_sql.text_to_sql.handler;

import com.text_to_sql.text_to_sql.common.enumeration.code.Difficulty;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Difficulty 枚举类型处理器
 * 数据库 TINYINT <-> Difficulty 枚举
 */
public class DifficultyHandler extends BaseTypeHandler<Difficulty> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Difficulty parameter, JdbcType jdbcType) throws SQLException {
		ps.setInt(i, parameter.getCode());
	}

	@Override
	public Difficulty getNullableResult(ResultSet rs, String columnName) throws SQLException {
		Integer code = rs.getInt(columnName);
		if (rs.wasNull()) {
			return null;
		}
		return Difficulty.fromCode(code);
	}

	@Override
	public Difficulty getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		Integer code = rs.getInt(columnIndex);
		if (rs.wasNull()) {
			return null;
		}
		return Difficulty.fromCode(code);
	}

	@Override
	public Difficulty getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		Integer code = cs.getInt(columnIndex);
		if (cs.wasNull()) {
			return null;
		}
		return Difficulty.fromCode(code);
	}
}
