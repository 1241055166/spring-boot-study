package com.henry.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * @Description:
 * @Author: huangjw-b
 */
@Configuration
@MapperScan(basePackages = "com.example.mapper", sqlSessionFactoryRef = "masterSqlSessionFactory")
public class MasterDataSourceConfig {
    @Autowired
    private Environment env;

    @Primary
    @Bean(name = "masterDataSource")
//    @ConfigurationProperties("spring.datasource.master")
    public DataSource masterDataSource() {
//        return DataSourceBuilder.create().build();
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(env.getProperty("spring.datasource.master.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.master.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.master.password"));
        dataSource.setDriverClassName(env.getProperty("spring.datasource.master.driver-class-name"));
        //配置初始化大小、最小、最大
        dataSource.setMinIdle(10);
        //配置初始化大小、最小、最大
        dataSource.setMaxActive(200);
        //配置初始化大小、最小、最大
        dataSource.setInitialSize(10);
        //配置获取连接等待超时的时间
        dataSource.setMaxWait(60000);
        //配置一个连接在池中最小生存的时间,单位是毫秒
        dataSource.setMinEvictableIdleTimeMillis(300000);
        //配置间隔多久才进行一次检测,检测需要关闭的空闲连接,单位是毫秒
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        //默认的testWhileIdle=true,testOnBorrow=false,testOnReturn=false
        dataSource.setValidationQuery("SELECT 1");
        //申请连接时执行validationQuery检测连接是否有效
        dataSource.setTestOnBorrow(false);
        //建议配置为true，不影响性能，并且保证安全性。
        dataSource.setTestWhileIdle(true);
        //是否缓存preparedStatement，也就是PSCache
        dataSource.setPoolPreparedStatements(false);
        return dataSource;
    }

    @Bean(name = "masterSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("masterDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);

        return sessionFactoryBean.getObject();
    }
}
