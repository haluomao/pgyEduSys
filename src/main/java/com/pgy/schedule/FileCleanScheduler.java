package com.pgy.schedule;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.pgy.common.CollectionHelper;
import com.pgy.config.ScheduleConfig;
import com.pgy.config.UploadConfig;
import com.pgy.material.MaterialManager;
import com.pgy.material.bean.Material;
import com.pgy.uploadlog.UploadLogManager;
import com.pgy.uploadlog.bean.UploadLog;

/**
 * The file clean scheduler.
 *
 * @author Felix
 */

@Component
@ManagedResource(objectName = "bean:name=FileCleanScheduler")
public class FileCleanScheduler extends BaseScheduler {

    private static final Log log = LogFactory.getLog(FileCleanScheduler.class);

    private static final String THREAD_POOL_NAME = "file-clean-scheduler";
    private ScheduleConfig scheduleConfig;
    private MaterialManager materialManager;
    private UploadLogManager uploadLogManager;
    private UploadConfig uploadConfig;

    @Autowired
    public FileCleanScheduler(ExecutorServiceFactory executorServiceFactory,
            ScheduleConfig scheduleConfig, MaterialManager materialManager, UploadLogManager uploadLogManager,
            UploadConfig uploadConfig) {
        super(THREAD_POOL_NAME, executorServiceFactory);
        this.scheduleConfig = scheduleConfig;
        disableScheduler = !scheduleConfig.isFileCleanEnable();
        this.materialManager = materialManager;
        this.uploadLogManager = uploadLogManager;
    }

    @Override
    protected void startCronTask() {
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                doFileClean();
            }
        }, new CronTrigger(scheduleConfig.getFileCleanCron()));
    }

    @VisibleForTesting
    @ManagedOperation
    public void doFileClean() {
        // TODO test.
        log.info("Cleaning files.");
        List<Material> materials = materialManager.getMaterials(Collections.<Long>emptyList());
        final Set<String> urls = Sets.newHashSet();
        for (Material material : materials) {
            urls.add(material.getUrl());
            urls.add(material.getIcon());
        }

        List<UploadLog> uploadLogs = CollectionHelper.filter(uploadLogManager.getUploadLogs(Collections.EMPTY_LIST),
                new Predicate<UploadLog>() {
                    @Override
                    public boolean apply(UploadLog input) {
                        return !urls.contains(input.getUrl());
                    }
                });

        try {
            List<Long> toDeleteLogIds = Lists.newArrayList();
            for (UploadLog uploadLog : uploadLogs) {
                String fileDir = uploadConfig.getLocal().getDir() + File.separator + uploadLog.getSignature();
                log.info("Cleaning dir:" + fileDir);
                FileUtils.moveDirectory(new File(fileDir), new File(uploadConfig.getLocal().getTrashDir()));
                toDeleteLogIds.add(uploadLog.getId());
            }
            uploadLogManager.delete(toDeleteLogIds);
        } catch (Exception e) {
            log.error(e);
        }
    }
}
