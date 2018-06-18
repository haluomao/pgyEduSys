package com.pgy.uploadlog;

import java.util.List;

import com.pgy.uploadlog.bean.UploadLog;

/**
 * The upload log manager.
 *
 * @author Felix
 */
public interface UploadLogManager {

    List<UploadLog> getUploadLogs(List<Long> ids);

    UploadLog detail(long id);

    int create(UploadLog entity);

    int update(UploadLog entity);

    void delete(List<Long> ids);

    UploadLog detail(String signature);
}
