package com.pgy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pgy.account.bean.Account;
import com.pgy.account.bean.AccountRequest;

/**
 * Account mapper.
 *
 * @author Felix
 */
@Mapper
public interface AccountMapper {
    List<Account> listByIds(@Param("ids") List<Long> ids);

    List<Account> query(@Param("query") AccountRequest request);

    int create(Account entity);

    void updatePwd(Account account);

    int update(Account account);

    int updateStatus(@Param("ids") List<Long> ids, @Param("status") int status);

    void delete(List<Long> ids);

    Account detail(@Param("id") long id);

    Account getAccount(@Param("name") String name);

    int checkAccount(@Param("name") String name, @Param("pwd") String password);

    int countAccount(@Param("parentId") long parentId, @Param("role") int role);

    int getAccountCount(@Param("id") long id, @Param("name") String name);
}
