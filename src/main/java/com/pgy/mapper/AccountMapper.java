package com.pgy.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pgy.account.bean.Account;

/**
 * Account mapper.
 *
 * @author Felix
 */
@Mapper
public interface AccountMapper {
//    List<Account> list(@Param("parentId") long parentId);
//
//    void create(Account entity);
//
//    void update(Account account);
//
//    void delete(long id);

    Account detail(@Param("id") long id);
}
