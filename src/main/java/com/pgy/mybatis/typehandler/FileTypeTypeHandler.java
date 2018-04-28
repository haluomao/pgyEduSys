package com.pgy.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

import com.pgy.common.bean.FileType;

/**
 * FileType type handler.
 *
 * @author Felix
 */
@MappedTypes(FileType.class)
@Component
public class FileTypeTypeHandler extends BaseTypeHandler<FileType> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, FileType parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public FileType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return FileType.parse(rs.getInt(columnName));
    }

    @Override
    public FileType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return FileType.parse(rs.getInt(columnIndex));
    }

    @Override
    public FileType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return FileType.parse(cs.getInt(columnIndex));
    }
}
