package com.pgy.test;

import java.util.List;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

/**
 * Databases used in intTest.
 *
 * @author Felix
 */
public class Database {

    private String sqlSessionFactoryName;
    private String location;
    private List<String> tables;

    public String getSqlSessionFactoryName() {
        return sqlSessionFactoryName;
    }

    public void setSqlSessionFactoryName(String sqlSessionFactoryName) {
        this.sqlSessionFactoryName = sqlSessionFactoryName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getTables() {
        return tables;
    }

    public void setTables(List<String> tables) {
        this.tables = tables;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{Database, sqlSessionFactoryName=");
        builder.append(sqlSessionFactoryName);
        builder.append(", location=");
        builder.append(location);
        builder.append(", tables=");
        builder.append(tables);
        builder.append("}");
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Database)) {
            return false;
        }
        Database database = (Database) o;
        return Objects.equal(sqlSessionFactoryName, database.sqlSessionFactoryName)
                && Objects.equal(location, database.location)
                && Objects.equal(tables, database.tables);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(sqlSessionFactoryName, location, tables);
    }

    public static final class DatabaseBuilder {
        private String sqlSessionFactoryName;
        private String location;
        private List<String> tables;

        private DatabaseBuilder() {
        }

        public static DatabaseBuilder aDatabase() {
            return new DatabaseBuilder();
        }

        public DatabaseBuilder withSqlSessionFactoryName(String sqlSessionFactoryName) {
            this.sqlSessionFactoryName = sqlSessionFactoryName;
            return this;
        }

        public DatabaseBuilder withLocation(String location) {
            this.location = location;
            return this;
        }

        public DatabaseBuilder withTables(List<String> tables) {
            this.tables = tables;
            return this;
        }

        public DatabaseBuilder withTables(String[] tables) {
            this.tables = Lists.newArrayList(tables);
            return this;
        }

        public Database build() {
            Database database = new Database();
            database.setSqlSessionFactoryName(sqlSessionFactoryName);
            database.setLocation(location);
            database.setTables(tables);
            return database;
        }
    }
}
