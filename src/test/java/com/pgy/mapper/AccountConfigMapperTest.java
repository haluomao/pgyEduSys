package com.pgy.mapper;

import java.text.ParseException;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.pgy.account.bean.AccountConfig;
import com.pgy.common.Constant;
import com.pgy.common.bean.Status;
import com.pgy.mybatis.DbFactory;
import com.pgy.test.BizDbTestCase;
import com.pgy.test.MockDatabase;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link AccountConfigMapper}
 *
 * @author Felix
 */
@MockDatabase(sqlSessionFactoryName = DbFactory.SQL_SESSION_FACTORY,
        location = BizDbTestCase.DATASET_BIZ,
        tables = "account_config")
public class AccountConfigMapperTest extends BizDbTestCase {

    private static final long ACCOUNT_ID_1 = 1L;

    @Autowired
    private AccountConfigMapper accountConfigMapper;

    @Test
    public void listByAccountIds() throws ParseException {
        assertEquals(Lists.newArrayList(buildAccountConfig()),
                accountConfigMapper.listByAccountIds(Lists.newArrayList(ACCOUNT_ID_1)));
    }

    @Test
    public void detail() throws ParseException {
        assertEquals(buildAccountConfig(), accountConfigMapper.detail(1L));
    }

    @Test
    public void create() throws ParseException {
        accountConfigMapper.create(buildAccountConfig());
    }

    @Test
    public void update() throws ParseException {
        accountConfigMapper.update(buildAccountConfig());
    }

    @Test
    public void updateStorage() throws ParseException {
        accountConfigMapper.updateStorage(buildAccountConfig());
    }

    @Test
    public void deleteByAccountId() throws ParseException {
        accountConfigMapper.deleteByAccountId(Lists.newArrayList(ACCOUNT_ID_1));
    }

    public AccountConfig buildAccountConfig() throws ParseException {
        AccountConfig account = new AccountConfig();
        account.setId(1L);
        account.setAccountId(ACCOUNT_ID_1);
        account.setTeacherLimit(10);
        account.setParentLimit(50);
        account.setStorageLimit(1000);
        account.setStorageUsed(0);
        account.setBeginTime(DateUtils.parseDate("2016-06-23 20:40:16", Constant.DATE_FULL_PATTERN));
        account.setEndTime(DateUtils.parseDate("2016-06-23 20:40:16", Constant.DATE_FULL_PATTERN));
        account.setStatus(Status.ENABLED);
        return account;
    }
}
