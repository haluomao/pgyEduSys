package com.pgy.mybatis;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.BaseTypeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.pgy.config.DbConfig;

/**
 * Db factory.
 *
 * @author Felix
 */
@Component
public class DbFactory extends AbstractDbFactory {
    private static final Log log = LogFactory.getLog(DbFactory.class);

    private static final String TYPE_HANDLERS_PACKAGE = "com.pgy";
    public static final String SQL_SESSION_FACTORY = "sqlSessionFactory";

    private final DataSource dataSource;

    @Autowired
    public DbFactory(DbConfig dbConfig, ResourceLoader resourceLoader, BaseTypeHandler[] typeHandlers) {
        super(new Interceptor[]{}, TYPE_HANDLERS_PACKAGE, resourceLoader, typeHandlers);
        dataSource = createDataSource(dbConfig.getDbUrl(),
                dbConfig.getDbUser(),
                dbConfig.getDbPassword());
    }

    @Bean(name = SQL_SESSION_FACTORY)
    public SqlSessionFactory createSqlSessionFactoryBean() throws Exception {
        return createSqlSessionFactoryBean(dataSource,
                "classpath*:com/pgy/mapper/*Mapper.xml");
    }
}