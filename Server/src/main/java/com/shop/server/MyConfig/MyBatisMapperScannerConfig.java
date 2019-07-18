package com.shop.server.MyConfig;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(MyBatisConfig.class)//在这个类之后执行
public class MyBatisMapperScannerConfig {
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mc=new MapperScannerConfigurer();
        mc.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mc.setBasePackage("com.shop.server.Mapper");

        return mc;
    }
}
