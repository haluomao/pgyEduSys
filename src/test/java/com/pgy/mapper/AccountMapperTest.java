package com.pgy.mapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pgy.account.bean.Account;
import com.pgy.account.bean.Role;
import com.pgy.common.bean.Status;
import com.pgy.mybatis.DbFactory;
import com.pgy.test.BizDbTestCase;
import com.pgy.test.MockDatabase;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link AccountMapper}
 *
 * @author Felix
 */
@MockDatabase(sqlSessionFactoryName = DbFactory.SQL_SESSION_FACTORY,
        location = BizDbTestCase.DATASET_BIZ,
        tables = "accounts")
public class AccountMapperTest extends BizDbTestCase {

    private static final String ADMIN_NAME = "admin";
    private static final long ACCOUNT_ID1 = 1L;
    private static final String ACCOUNT_ID1_PASS = "743894a0e4a801fc321232f297a57a5a";

    @Autowired
    private AccountMapper accountMapper;

    @Test
    public void detail() {
        assertEquals(null, accountMapper.detail(3L));
        assertEquals(buildAccountAdmin(), accountMapper.detail(ACCOUNT_ID1));
    }

    public Account buildAccountAdmin() {
        Account account = new Account();
        account.setId(ACCOUNT_ID1);
        account.setAccountName("admin");
        account.setAccountPassword("21232f297a57a5a743894a0e4a801fc3");
        account.setRole(Role.ADMIN);
        account.setEmail("a@a.com");
        account.setPhone("1234567890");
        account.setParentId(0L);
        account.setStatus(Status.ENABLED);
        return account;
    }
}
