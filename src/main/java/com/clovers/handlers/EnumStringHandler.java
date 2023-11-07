package com.clovers.handlers;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import com.clovers.dto.AdminDTO.AuthorityCategories;


@MappedTypes({AuthorityCategories.class})
public class EnumStringHandler<E extends Enum<E>> extends BaseTypeHandler<E> {
    private Class<E> type;

    // 생성자에서 Enum 클래스 타입을 받아와 type 필드에 저장.
    public EnumStringHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    // PreparedStatement에 Enum 값을 String으로 설정할 때 사용.
    // Enum의 이름을 String으로 변환하여 SQL 쿼리에 바인딩.
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name());
    }

    // ResultSet에서 컬럼 이름으로 Enum 값을 얻어올 때 사용.
    // String 값을 Enum으로 변환하여 반환.
    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String name = rs.getString(columnName);
        if (name == null) {
            return null; // DB에서 null이면 Java에서도 null을 반환
        }
        return Enum.valueOf(type, name); // String 값을 Enum으로 변환
    }

    // ResultSet에서 컬럼 인덱스로 Enum 값을 얻어올 때 사용.
    // String 값을 Enum으로 변환하여 반환.
    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String name = rs.getString(columnIndex);
        if (name == null) {
            return null; // DB에서 null이면 Java에서도 null을 반환
        }
        return Enum.valueOf(type, name); // String 값을 Enum으로 변환
    }

    // CallableStatement에서 컬럼 인덱스로 Enum 값을 얻어올 때 사용.
    // String 값을 Enum으로 변환하여 반환.
    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String name = cs.getString(columnIndex);
        if (name == null) {
            return null; // DB에서 null이면 Java에서도 null을 반환
        }
        return Enum.valueOf(type, name); // String 값을 Enum으로 변환
    }
}