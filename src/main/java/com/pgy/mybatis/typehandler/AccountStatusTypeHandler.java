package com.pgy.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

import com.pgy.account.bean.AccountStatus;

/**
 * AccountStatus type handler.
 *
 * @author Felix
 */
@MappedTypes(AccountStatus.class)
@Component
public class AccountStatusTypeHandler extends BaseTypeHandler<AccountStatus> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, AccountStatus parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public AccountStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return AccountStatus.parse(rs.getInt(columnName));
    }

    @Override
    public AccountStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return AccountStatus.parse(rs.getInt(columnIndex));
    }

    @Override
    public AccountStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return AccountStatus.parse(cs.getInt(columnIndex));
    }
}
