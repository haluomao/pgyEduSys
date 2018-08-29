package com.pgy.schedule;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.pgy.account.AccountManager;
import com.pgy.account.bean.Account;
import com.pgy.account.bean.AccountStatus;
import com.pgy.common.CollectionHelper;
import com.pgy.config.ScheduleConfig;

/**
 * The user check scheduler.
 *
 * @author Felix
 */

@Component
@ManagedResource(objectName = "bean:name=UserCheckScheduler")
public class UserCheckScheduler extends BaseScheduler {

    private static final Log log = LogFactory.getLog(UserCheckScheduler.class);

    private static final String THREAD_POOL_NAME = "user-check-scheduler";
    private ScheduleConfig scheduleConfig;
    private AccountManager accountManager;

    @Autowired
    public UserCheckScheduler(ExecutorServiceFactory executorServiceFactory,
            ScheduleConfig scheduleConfig, AccountManager accountManager) {
        super(THREAD_POOL_NAME, executorServiceFactory);
        this.scheduleConfig = scheduleConfig;
        disableScheduler = !scheduleConfig.isUserCheckEnable();
        this.accountManager = accountManager;
    }

    @Override
    protected void startCronTask() {
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                doUserCheck();
            }
        }, new CronTrigger(scheduleConfig.getUserCheckCron()));
    }

    @VisibleForTesting
    @ManagedOperation
    public void doUserCheck() {
        final Date now = new Date();
        List<Account> expiredAccountList = CollectionHelper.filter(
                accountManager.getAccounts(Collections.EMPTY_LIST),
                new Predicate<Account>() {
                    @Override
                    public boolean apply(Account input) {
                        return input.getStatus() == AccountStatus.ENABLED &&
                                input.getEndTime() != null && input.getEndTime().before(now);
                    }
                });
        if (CollectionUtils.isNotEmpty(expiredAccountList)) {
            accountManager.updateStatus(CollectionHelper.transform(expiredAccountList,
                    new Function<Account, Long>() {
                        @Override
                        public Long apply(Account input) {
                            return input.getId();
                        }
                    }),
                    AccountStatus.EXPIRED.getValue());
        }
    }
}
