package com.pgy.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

import com.pgy.material.bean.MaterialStatus;

/**
 * MaterialStatus type handler.
 *
 * @author Felix
 */
@MappedTypes(MaterialStatus.class)
@Component
public class MaterialStatusTypeHandler extends BaseTypeHandler<MaterialStatus> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, MaterialStatus parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public MaterialStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return MaterialStatus.parse(rs.getInt(columnName));
    }

    @Override
    public MaterialStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return MaterialStatus.parse(rs.getInt(columnIndex));
    }

    @Override
    public MaterialStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return MaterialStatus.parse(cs.getInt(columnIndex));
    }
}
