package com.pgy.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

import com.pgy.auth.bean.Role;

/**
 * Role type handler.
 *
 * @author Felix
 */
@MappedTypes(Role.class)
@Component
public class RoleTypeHandler extends BaseTypeHandler<Role> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Role parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public Role getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return Role.parse(rs.getInt(columnName));
    }

    @Override
    public Role getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return Role.parse(rs.getInt(columnIndex));
    }

    @Override
    public Role getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return Role.parse(cs.getInt(columnIndex));
    }
}
