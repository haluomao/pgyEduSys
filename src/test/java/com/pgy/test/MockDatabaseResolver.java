package com.pgy.test;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import org.dbunit.dataset.csv.CsvDataSet;
import org.dbunit.dataset.csv.CsvProducer;

import com.google.common.collect.Lists;

/**
 * Resolve {@link MockDatabase} and {@link MockDatabases}
 *
 * @author Felix
 */
public class MockDatabaseResolver {

    private final Class<? extends DatabaseTestCase> clazz;

    public MockDatabaseResolver(Class<? extends DatabaseTestCase> clazz) {
        this.clazz = clazz;
    }

    public List<Database> getNeededDatabases() throws IOException, IllegalAccessException {
        MockDatabases mockDatabases = clazz.getAnnotation(MockDatabases.class);
        if (mockDatabases != null) {
            return parseNeededDatabases(mockDatabases);
        }
        MockDatabase mockDatabase = clazz.getAnnotation(MockDatabase.class);
        if (mockDatabase != null) {
            return Lists.newArrayList(parseNeededDatabase(mockDatabase));
        }
        throw new IllegalAccessException("Lack of mock database annotation.");
    }

    private List<Database> parseNeededDatabases(
            MockDatabases mockDatabases) throws IOException {
        List<Database> databases = Lists.newArrayList();
        for (MockDatabase mockDatabase : mockDatabases.value()) {
            databases.add(parseNeededDatabase(mockDatabase));
        }
        return databases;
    }

    private Database parseNeededDatabase(
            MockDatabase mockDatabase) throws IOException {
        Database database = Database.DatabaseBuilder.aDatabase()
                .withSqlSessionFactoryName(mockDatabase.sqlSessionFactoryName())
                .withLocation(mockDatabase.location())
                .withTables(mockDatabase.tables())
                .build();

        // Ensure order.
        LinkedHashSet<String> tableSet = new LinkedHashSet<String>(loadAllTables(database));
        tableSet.retainAll(Arrays.asList(mockDatabase.tables()));

        // Check data exist.
        for (String table : mockDatabase.tables()) {
            if (!tableSet.contains(table)) {
                throw new RuntimeException(String.format("Table data %s%s.csv not exist.",
                        mockDatabase.location(), table));
            }
        }

        return database;
    }

    private List<String> loadAllTables(Database database) throws IOException {
        @SuppressWarnings("unchecked")
        List<String> tables = CsvProducer.getTables(
                getClass().getResource(database.getLocation()),
                CsvDataSet.TABLE_ORDERING_FILE);
        return tables;
    }

}
