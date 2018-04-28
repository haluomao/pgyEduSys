package com.pgy.test;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.mybatis.spring.SqlSessionUtils;

import com.google.common.collect.Lists;
import com.pgy.test.dataset.csv.CsvURLDataSet;

/**
 * Database resource.
 *
 * @author Felix
 */
public class DatabaseResource {

    private final SqlSessionFactory sqlSessionFactory;
    private final String location;
    private final List<String> tables;
    private SqlSession sqlSession = null;

    public DatabaseResource(SqlSessionFactory sqlSessionFactory, String location,
            Collection<String> tables) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.location = location;
        this.tables = Lists.newArrayList(tables);
    }

    public void before() throws Throwable {
        sqlSession = sqlSessionFactory.openSession();
        DatabaseConnection databaseConnection = new DatabaseConnection(sqlSession.getConnection());
        databaseConnection.getConfig().setProperty(
                DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
                new MySqlDataTypeFactory());
        IDataSet dataset = new CsvURLDataSet(getClass().getResource(location),
                tables, "UTF-8");
        DatabaseOperation.CLEAN_INSERT.execute(databaseConnection, dataset);
    }

    public void after() {
        releaseResource();
    }

    private void releaseResource() {
        SqlSessionUtils.closeSqlSession(sqlSession, sqlSessionFactory);
    }
}
