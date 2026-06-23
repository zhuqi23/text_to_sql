package com.text_to_sql.text_to_sql.handler;

import com.text_to_sql.text_to_sql.common.enumeration.code.UserStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserStatusHandler extends BaseTypeHandler<UserStatus> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, UserStatus parameter, JdbcType jdbcType) throws SQLException {
		ps.setInt(i, parameter.getCode());
	}

	@Override
	public UserStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
		Integer code = rs.getInt(columnName);
		if (rs.wasNull()) {
			return null;
		}
		return UserStatus.fromCode(code);
	}

	@Override
	public UserStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		Integer code = rs.getInt(columnIndex);
		if (rs.wasNull()) {
			return null;
		}
		return UserStatus.fromCode(code);
	}

	@Override
	public UserStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		Integer code = cs.getInt(columnIndex);
		if (cs.wasNull()) {
			return null;
		}
		return UserStatus.fromCode(code);
	}
}
