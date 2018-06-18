package com.pgy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pgy.uploadlog.bean.UploadLog;

/**
 * Upload log mapper.
 *
 * @author Felix
 */
@Mapper
public interface UploadLogMapper {
    List<UploadLog> getUploadLogs(@Param("ids") List<Long> ids);

    int create(UploadLog entity);

    int update(UploadLog entity);

    void delete(List<Long> ids);

    UploadLog detail(@Param("id") long id);

    UploadLog getBySignature(@Param("signature") String signature);
}
