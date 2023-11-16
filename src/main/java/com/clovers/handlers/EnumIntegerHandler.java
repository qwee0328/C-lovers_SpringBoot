package com.clovers.handlers;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import com.clovers.dto.ChatDTO.ChatRoomStates;
import com.clovers.dto.ChatMessageDTO.ChatMessageStates;

@MappedTypes({ChatRoomStates.class, ChatMessageStates.class})
public class EnumIntegerHandler<E extends Enum<E>> extends BaseTypeHandler<E> {
    private Class<E> type;
    private E[] enums;

    public EnumIntegerHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
        this.enums = type.getEnumConstants();
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.ordinal());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int ordinal = rs.getInt(columnName);
        if (rs.wasNull()) {
            return null;
        }
        return toEnum(ordinal);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int ordinal = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return null;
        }
        return toEnum(ordinal);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int ordinal = cs.getInt(columnIndex);
        if (cs.wasNull()) {
            return null;
        }
        return toEnum(ordinal);
    }

    private E toEnum(int ordinal) {
        if (ordinal < 0 || ordinal >= enums.length) {
            throw new IllegalArgumentException("Unknown ordinal value for enum " + type.getSimpleName() + ": " + ordinal);
        }
        return enums[ordinal];
    }
}
