package com.pgy.test;

import java.util.List;

import org.junit.rules.ExternalResource;

import com.google.common.collect.Lists;

/**
 * Manager test resources.
 *
 * @author Felix
 */
public class ExternalResources extends ExternalResource {

    private List<DatabaseResource> resources = Lists.newArrayList();

    public void addResource(DatabaseResource resource) {
        resources.add(resource);
    }

    @Override
    protected void before() throws Throwable {
        Throwable throwable = null;
        for (DatabaseResource resource : resources) {
            try {
                resource.before();
            } catch (Throwable t) {
                throwable = t;
            }
        }
        if (throwable != null) {
            throw throwable;
        }
    }

    @Override
    protected void after() {
        for (DatabaseResource resource : resources) {
            try {
                resource.after();
            } catch (Throwable throwable) {
                // Just continue.
            }
        }
    }
}
