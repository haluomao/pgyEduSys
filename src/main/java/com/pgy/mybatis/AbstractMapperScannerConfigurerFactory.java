package com.pgy.mybatis;

import org.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * The mapper scanner for different datasources.
 *
 * @author Felix
 */
public abstract class AbstractMapperScannerConfigurerFactory {

    protected MapperScannerConfigurer createMapperScannerConfigurer(String packageName, String sqlSessionFactoryName) {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage(packageName);
        configurer.setSqlSessionFactoryBeanName(sqlSessionFactoryName);
        return configurer;
    }
}
