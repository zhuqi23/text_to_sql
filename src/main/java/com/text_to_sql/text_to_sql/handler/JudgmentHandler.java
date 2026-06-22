package com.text_to_sql.text_to_sql.handler;

import com.text_to_sql.text_to_sql.common.enumeration.code.Judgment;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JudgmentHandler extends BaseTypeHandler<Judgment> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Judgment parameter, JdbcType jdbcType) throws SQLException {
		ps.setInt(i, parameter.getCode());
	}

	@Override
	public Judgment getNullableResult(ResultSet rs, String columnName) throws SQLException {
		Integer code = rs.getInt(columnName);
		if (rs.wasNull()) {
			return null;
		}
		return Judgment.fromCode(code);
	}

	@Override
	public Judgment getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		Integer code = rs.getInt(columnIndex);
		if (rs.wasNull()) {
			return null;
		}
		return Judgment.fromCode(code);
	}

	@Override
	public Judgment getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		Integer code = cs.getInt(columnIndex);
		if (cs.wasNull()) {
			return null;
		}
		return Judgment.fromCode(code);
	}
}
