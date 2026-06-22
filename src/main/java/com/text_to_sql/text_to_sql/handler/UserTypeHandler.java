package com.text_to_sql.text_to_sql.handler;

import com.text_to_sql.text_to_sql.common.enumeration.code.UserType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * UserType 枚举类型处理器
 * 数据库 TINYINT <-> UserType 枚举
 */
public class UserTypeHandler extends BaseTypeHandler<UserType> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, UserType parameter, JdbcType jdbcType) throws SQLException {
		ps.setInt(i, parameter.getCode());
	}

	@Override
	public UserType getNullableResult(ResultSet rs, String columnName) throws SQLException {
		Integer code = rs.getInt(columnName);
		if (rs.wasNull()) {
			return null;
		}
		return UserType.fromCode(code);
	}

	@Override
	public UserType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		Integer code = rs.getInt(columnIndex);
		if (rs.wasNull()) {
			return null;
		}
		return UserType.fromCode(code);
	}

	@Override
	public UserType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		Integer code = cs.getInt(columnIndex);
		if (cs.wasNull()) {
			return null;
		}
		return UserType.fromCode(code);
	}
}
