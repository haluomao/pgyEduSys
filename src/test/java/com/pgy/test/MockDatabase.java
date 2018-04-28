package com.pgy.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the data of the given database and tables should be mocked before
 * executing test cases. Put it on subclasses of {@link DatabaseTestCase}.
 * Data inserting order is defined in table-ordering.txt.
 *
 * @author Felix
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MockDatabase {

    String sqlSessionFactoryName();

    String location();

    String[] tables();
}
