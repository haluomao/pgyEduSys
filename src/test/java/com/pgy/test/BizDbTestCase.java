package com.pgy.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Biz test case.
 *
 * @author Felix
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:com/pgy/test/test-beans.xml" })
public abstract class BizDbTestCase extends DatabaseTestCase {

    public static final String DATASET_BIZ = "/com/pgy/test/mapper/dataset/";
}
