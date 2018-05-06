package com.pgy.mapper;

import java.text.ParseException;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.pgy.account.bean.Account;
import com.pgy.account.bean.AccountRequest;
import com.pgy.account.bean.AccountStatus;
import com.pgy.auth.bean.Role;
import com.pgy.common.Constant;
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
    private static final long ACCOUNT_ID_1 = 1L;
    private static final String ACCOUNT_ID1_PASS = "743894a0e4a801fc321232f297a57a5a";

    @Autowired
    private AccountMapper accountMapper;

    @Test
    public void list() {
        assertEquals(Lists.newArrayList(buildAccountAdmin()),
                accountMapper.listByIds(Lists.newArrayList(ACCOUNT_ID_1)));
    }

    @Test
    public void query() {
        assertEquals(Lists.newArrayList(buildAccountAdmin()),
                accountMapper.query(buildAccountRequest()));
    }

    @Test
    public void create() {
        accountMapper.create(buildAccountAdmin());
    }

    @Test
    public void delete() {
        accountMapper.delete(Lists.newArrayList(ACCOUNT_ID_1));
    }

    @Test
    public void update() {
        Account account = buildAccountAdmin();
        account.setEmail("email");
        accountMapper.update(account);
        assertEquals("email", accountMapper.detail(account.getId()).getEmail());
    }

    @Test
    public void updatePwd() {
        Account account = buildAccountAdmin();
        account.setAccountPassword("pwd");
        accountMapper.updatePwd(account);
        assertEquals("pwd", accountMapper.detail(account.getId()).getAccountPassword());
    }

    @Test
    public void detail() {
        assertEquals(null, accountMapper.detail(3L));
        assertEquals(buildAccountAdmin(), accountMapper.detail(ACCOUNT_ID_1));
    }

    @Test
    public void getAccount() {
        assertEquals(buildAccountAdmin(), accountMapper.getAccount("admin"));
    }

    @Test
    public void checkAccount() {
        assertEquals(1, accountMapper.checkAccount("admin", "21232f297a57a5a743894a0e4a801fc3"));
        assertEquals(0, accountMapper.checkAccount("admin1", "21232f297a57a5a743894a0e4a801fc3"));
    }

    @Test
    public void countAccount() {
        assertEquals(1, accountMapper.countAccount(0, Role.ADMIN.getValue()));
    }

    private AccountRequest buildAccountRequest() {
        return AccountRequest.Builder.anAccountRequest()
                .withAccountName("admin")
                .build();
    }

    private Account buildAccountAdmin() {
        Account account = new Account();
        account.setId(ACCOUNT_ID_1);
        account.setAccountName("admin");
        account.setAccountPassword("21232f297a57a5a743894a0e4a801fc3");
        account.setUsername("user");
        account.setRole(Role.ADMIN);
        account.setEmail("a@a.com");
        account.setPhone("1234567890");
        account.setPermission("ALL");
        account.setParentId(0L);
        account.setStatus(AccountStatus.ENABLED);
        try {
            account.setBeginTime(DateUtils.parseDate("2016-06-23 20:40:16", Constant.DATE_FULL_PATTERN));
            account.setEndTime(DateUtils.parseDate("2016-06-23 20:40:16", Constant.DATE_FULL_PATTERN));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return account;
    }
}
