package com.pgy.mapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.pgy.material.bean.MaterialStatus;
import com.pgy.mybatis.DbFactory;
import com.pgy.test.BizDbTestCase;
import com.pgy.test.MockDatabase;
import com.pgy.uploadlog.bean.UploadLog;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link UploadLogMapper}
 *
 * @author Felix
 */
@MockDatabase(sqlSessionFactoryName = DbFactory.SQL_SESSION_FACTORY,
        location = BizDbTestCase.DATASET_BIZ,
        tables = "upload_log")
public class UploadLogMapperTest extends BizDbTestCase {

    private static final long ID_1 = 1L;
    private static final long ID_2 = 2L;

    @Autowired
    private UploadLogMapper materialMapper;

    @Test
    public void getUploadLogs() {
        assertEquals(Lists.newArrayList(buildUploadLog1()),
                materialMapper.getUploadLogs(Lists.<Long>newArrayList(ID_1)));
    }

    @Test
    public void detail() {
        assertEquals(null, materialMapper.detail(3L));
        assertEquals(buildUploadLog1(), materialMapper.detail(ID_1));
    }

    @Test
    public void getBySignature() {
        assertEquals(null, materialMapper.getBySignature("sign"));
        assertEquals(buildUploadLog1(), materialMapper.getBySignature("sign1"));
    }

    @Test
    public void create() {
        UploadLog uploadLog = buildUploadLog2();
        materialMapper.create(uploadLog);
        // assertEquals(material, materialMapper.detail(material.getId()));
    }

    @Test
    public void update() {
        UploadLog uploadLog = buildUploadLog2();
        assertEquals(1, materialMapper.update(uploadLog));
    }

    @Test
    public void delete() {
        materialMapper.delete(Lists.newArrayList(ID_1));
        assertEquals(null, materialMapper.detail(ID_1));
    }

    private UploadLog buildUploadLog1() {
        UploadLog uploadLog = new UploadLog();
        uploadLog.setId(ID_1);
        uploadLog.setFilename("file1");
        uploadLog.setFileType("img");
        uploadLog.setUrl("url1");
        uploadLog.setSignature("sign1");
        uploadLog.setPath("url1");
        uploadLog.setDownloadUrl("dUrl1");
        uploadLog.setUploaderId(1);
        uploadLog.setSize(100);
        uploadLog.setStatus(MaterialStatus.ENABLED);
        return uploadLog;
    }

    private UploadLog buildUploadLog2() {
        UploadLog uploadLog = new UploadLog();
        uploadLog.setId(ID_2);
        uploadLog.setFilename("file2");
        uploadLog.setFileType("zip");
        uploadLog.setUrl("url2");
        uploadLog.setSignature("sign2");
        uploadLog.setPath("url2");
        uploadLog.setDownloadUrl("dUrl2");
        uploadLog.setUploaderId(2);
        uploadLog.setSize(100);
        uploadLog.setStatus(MaterialStatus.ENABLED);
        return uploadLog;
    }
}
