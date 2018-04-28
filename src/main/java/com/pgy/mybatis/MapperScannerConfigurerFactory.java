package com.pgy.mybatis;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Mapper scanner.
 *
 * @author Felix
 */
@Component
public class MapperScannerConfigurerFactory extends AbstractMapperScannerConfigurerFactory{
    @Bean(name = "mapperScannerConfigurer")
    public MapperScannerConfigurer createMapperScannerConfigurerBean() {
        return createMapperScannerConfigurer("com.pgy.mapper",
                "sqlSessionFactory");
    }
}
