package com.pgy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pgy.account.bean.AccountConfig;

/**
 * Account config mapper.
 *
 * @author Felix
 */
@Mapper
public interface AccountConfigMapper {
    List<AccountConfig> listByAccountIds(@Param("accountIds") List<Long> ids);

    AccountConfig detail(@Param("configId") long id);

    void create(AccountConfig accountConfig);

    int update(AccountConfig accountConfig);

    int updateStorage(AccountConfig accountConfig);

    void deleteByAccountId(@Param("accountIds") List<Long> ids);

}
