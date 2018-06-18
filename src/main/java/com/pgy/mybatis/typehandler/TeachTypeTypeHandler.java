package com.pgy.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

import com.pgy.material.bean.TeachType;

/**
 * TeachType type handler.
 *
 * @author Felix
 */
@MappedTypes(TeachType.class)
@Component
public class TeachTypeTypeHandler extends BaseTypeHandler<TeachType> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, TeachType parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public TeachType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return TeachType.parse(rs.getInt(columnName));
    }

    @Override
    public TeachType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return TeachType.parse(rs.getInt(columnIndex));
    }

    @Override
    public TeachType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return TeachType.parse(cs.getInt(columnIndex));
    }
}
