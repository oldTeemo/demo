package com.taojingwei.demo.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages = "com.taojingwei.demo.dao.demo2", sqlSessionFactoryRef = "db2SqlSessionFactory")
public class Demo2DataSourceConfig {

    @Bean("db2DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.datasource2") //读取application.yml中的配置参数映射成为一个对象
    public DataSource getDb1DataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean("db2SqlSessionFactory")
    public SqlSessionFactory db1SqlSessionFactory(@Qualifier("db2DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        // mapper的xml形式文件位置必须要配置，不然将报错：no statement （这种错误也可能是mapper的xml中，namespace与项目的路径不一致导致）
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/dbdemo2/*.xml"));
        return bean.getObject();
    }

    @Bean("datasourceTransactionManager2")
    public DataSourceTransactionManager datasourceTransactionManager(@Qualifier("db2DataSource") DataSource dataSource) throws Exception {
        DataSourceTransactionManager bean = new DataSourceTransactionManager();
        bean.setDataSource(dataSource);
        return bean;
    }
}
