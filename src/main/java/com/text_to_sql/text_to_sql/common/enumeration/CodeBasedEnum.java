package com.text_to_sql.text_to_sql.common.enumeration;

/**
 * 枚举类型接口
 * 基于 code 的枚举类型(用 code 字段和数据库交互)
 * 提取 UserType和Difficulty 枚举类型
 * 用于 TypeHandler 枚举类型转换: 枚举类型 <-> 数据库的int
 */
public interface CodeBasedEnum {
	Integer getCode();
	String getDescription();
}
