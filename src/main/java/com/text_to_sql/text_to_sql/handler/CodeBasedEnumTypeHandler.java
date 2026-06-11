package com.text_to_sql.text_to_sql.handler;

import com.text_to_sql.text_to_sql.common.enumeration.CodeBasedEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 自定义枚举类型处理器
 * code枚举类型 <-> 数据库中的int值
 * @param <E> 枚举类型
 */
public class CodeBasedEnumTypeHandler<E extends CodeBasedEnum> extends BaseTypeHandler<E> {

	private final Class<E> enumClass;
	private final Method fromCodeMethod;

	public CodeBasedEnumTypeHandler(Class<E> enumClass) {
		this.enumClass = enumClass;

		try {
			this.fromCodeMethod = enumClass.getMethod("fromCode", Integer.class);
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException("Enum " + enumClass.getSimpleName() +
					" must have a static fromCode(Integer) method", e);
		}
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
		ps.setInt(i, parameter.getCode());
	}

	@Override
	@SuppressWarnings("unchecked")
	public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
		Integer code = rs.getInt(columnName);
		if (rs.wasNull()) {
			return null;
		}
		try {
			return (E) fromCodeMethod.invoke(null, code);
		} catch (Exception e) {
			throw new RuntimeException("Failed to convert code to enum: " + code, e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		Integer code = rs.getInt(columnIndex);
		if (rs.wasNull()) {
			return null;
		}
		try {
			return (E) fromCodeMethod.invoke(null, code);
		} catch (Exception e) {
			throw new RuntimeException("Failed to convert code to enum: " + code, e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		Integer code = cs.getInt(columnIndex);
		if (cs.wasNull()) {
			return null;
		}
		try {
			return (E) fromCodeMethod.invoke(null, code);
		} catch (Exception e) {
			throw new RuntimeException("Failed to convert code to enum: " + code, e);
		}
	}
}
