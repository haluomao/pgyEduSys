package com.pgy.test;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Rule;
import org.springframework.context.ApplicationContext;

/**
 * The base dao test.
 *
 * @author Felix
 */
public abstract class DatabaseTestCase {

    @Rule
    public ExternalResources resources;

    @Resource
    public void setDataSources(ApplicationContext context) throws IOException, IllegalAccessException {
        resources = new ExternalResources();
        MockDatabaseResolver resolver = new MockDatabaseResolver(
                this.getClass());
        List<Database> databases = resolver.getNeededDatabases();
        for (Database database : databases) {
            DatabaseResource resource = new DatabaseResource(context.getBean(
                    database.getSqlSessionFactoryName(), SqlSessionFactory.class),
                    database.getLocation(), database.getTables());
            resources.addResource(resource);
        }
    }
}
