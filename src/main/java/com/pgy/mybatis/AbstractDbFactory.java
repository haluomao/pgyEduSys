package com.pgy.mybatis;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.BaseTypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * DB factory.
 *
 * @author Felix
 */
public abstract class AbstractDbFactory {

    private static final Log log = LogFactory.getLog(AbstractDbFactory.class);

    private static final String MYBATIS_SETTING_XML = "com/pgy/mybatis/mybatis-setting.xml";

    private final Interceptor[] plugins;
    private final String typeHandlersPackage;
    private final ResourceLoader resourceLoader;
    private final BaseTypeHandler[] typeHandlers;

    public AbstractDbFactory(Interceptor[] plugins, String typeHandlersPackage, ResourceLoader resourceLoader,
            BaseTypeHandler[] typeHandlers) {
        // NOTE: The order of plugins is important.
        this.plugins = plugins;
        this.typeHandlersPackage = typeHandlersPackage;
        this.resourceLoader = resourceLoader;
        this.typeHandlers = typeHandlers;
    }

    protected DataSourceTransactionManager createTransactionManager(DataSource dataSource) {
        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setDataSource(dataSource);
        return manager;
    }

    protected SqlSessionFactory createSqlSessionFactoryBean(String dbUrl, String dbUser, String dbPassword,
        String mapperLocation) throws Exception {
        DataSource dataSource = createDataSource(dbUrl, dbUser, dbPassword);
        return createSqlSessionFactoryBean(dataSource, mapperLocation);
    }

    protected SqlSessionFactory createSqlSessionFactoryBean(DataSource dataSource, String mapperLocation)
            throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);

        factory.setMapperLocations(ResourcePatternUtils.getResourcePatternResolver(resourceLoader)
                .getResources(mapperLocation));
        factory.setTypeHandlersPackage(typeHandlersPackage);
        factory.setTypeHandlers(typeHandlers);
        factory.setPlugins(plugins);
        factory.setConfigLocation(new ClassPathResource(MYBATIS_SETTING_XML));
        return factory.getObject();
    }

    protected DataSource createDataSource(String dbUrl, String dbUser, String dbPassword) {
        ComboPooledDataSource source = createBaseDataSource();
        source.setJdbcUrl(dbUrl);
        source.setUser(dbUser);
        source.setPassword(dbPassword);
        return source;
    }

    private ComboPooledDataSource createBaseDataSource() {
        ComboPooledDataSource source = new ComboPooledDataSource();
        try {
            source.setDriverClass("com.mysql.jdbc.Driver");
            source.setMinPoolSize(0);
            source.setMaxPoolSize(10);
            source.setInitialPoolSize(0);
            source.setMaxIdleTime(60);
            source.setAcquireIncrement(1);
            source.setMaxStatements(0);
            source.setIdleConnectionTestPeriod(60);
            source.setAcquireRetryAttempts(30);
            source.setBreakAfterAcquireFailure(false);
            source.setCheckoutTimeout(300000);
        } catch (PropertyVetoException e) {
            log.error("Error while create DB data source.", e);
            return null;
        }

        return source;
    }
}
