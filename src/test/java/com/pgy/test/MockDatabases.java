package com.pgy.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mock data of databases.
 *
 * @author Felix
 * @see MockDatabase
 * @see DatabaseTestCase
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MockDatabases {
    MockDatabase[] value();
}
