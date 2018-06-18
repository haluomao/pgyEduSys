package com.pgy.uploadlog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pgy.common.CollectionHelper;
import com.pgy.mapper.UploadLogMapper;
import com.pgy.uploadlog.bean.UploadLog;

/**
 * The impl of {@link UploadLogManager}.
 *
 * @author Felix
 */
@Component
public class UploadLogManagerImpl implements UploadLogManager {
    private final UploadLogMapper uploadLogMapper;

    @Autowired
    public UploadLogManagerImpl(UploadLogMapper uploadLogMapper) {
        this.uploadLogMapper = uploadLogMapper;
    }

    @Override
    public List<UploadLog> getUploadLogs(List<Long> ids) {
        return CollectionHelper.getNonNullList(uploadLogMapper.getUploadLogs(ids));
    }

    @Override
    public UploadLog detail(long id) {
        return uploadLogMapper.detail(id);
    }

    @Override
    public int create(UploadLog entity) {
        return uploadLogMapper.create(entity);
    }

    @Override
    public int update(UploadLog entity) {
        return uploadLogMapper.update(entity);
    }

    @Override
    public void delete(List<Long> ids) {
        uploadLogMapper.delete(ids);
    }

    @Override
    public UploadLog detail(String signature) {
        return uploadLogMapper.getBySignature(signature);
    }
}
