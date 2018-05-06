package com.pgy.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

import com.pgy.material.bean.PublicLevel;

/**
 * PublicLevel type handler.
 *
 * @author Felix
 */
@MappedTypes(PublicLevel.class)
@Component
public class PublicLevelTypeHandler extends BaseTypeHandler<PublicLevel> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, PublicLevel parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public PublicLevel getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return PublicLevel.parse(rs.getInt(columnName));
    }

    @Override
    public PublicLevel getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return PublicLevel.parse(rs.getInt(columnIndex));
    }

    @Override
    public PublicLevel getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return PublicLevel.parse(cs.getInt(columnIndex));
    }
}
