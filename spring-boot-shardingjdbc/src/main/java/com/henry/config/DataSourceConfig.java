package com.henry.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.github.pagehelper.PageInterceptor;
import com.google.common.collect.Lists;
import groovy.util.logging.Slf4j;
import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import io.shardingjdbc.core.api.config.strategy.InlineShardingStrategyConfiguration;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 * @Description：配置参考链接：https://shardingsphere.apache.org/document/legacy/3.x/document/cn/manual/sharding-jdbc/configuration/config-java/
 * @Author：henry
 * @Date：2021/11/15 上午11:44
 * @Versiion：1.0
 */
@Configuration
@EnableTransactionManagement
@Slf4j
public class DataSourceConfig {
    @Autowired
    private Environment env;

    @Bean
    public Filter statFilter() {
        StatFilter filter = new StatFilter();
        filter.setSlowSqlMillis(5000);
        filter.setLogSlowSql(true);
        filter.setMergeSql(true);
        return filter;
    }

    @Bean("sqlSessionFactory")
    SqlSessionFactory sqlSessionFactory(
    ) throws Exception {
        final MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();
        //设置数据源
        sessionFactory.setDataSource(dataSource());
        //设置分页
        sessionFactory.setPlugins(new Interceptor[]{mybatisPlusInterceptor(), pageInterceptor()});
        //mapper扫描路径
        Resource[] r1 = new PathMatchingResourcePatternResolver()
                .getResources("classpath*:com/henry/mapper/xml/*.xml");
        Resource[] r2 = new PathMatchingResourcePatternResolver()
                .getResources("classpath*:mapper/*.xml");
        List<Resource> list = new ArrayList<>();
        list.addAll(Arrays.asList(r1));
        list.addAll(Arrays.asList(r2));
        sessionFactory.setMapperLocations(list.toArray(new Resource[list.size()]));
        return sessionFactory.getObject();
    }

    //事务管理
    @Bean
    public DataSourceTransactionManager transactitonManager(@Autowired DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    //mybatis 分页
    public PageInterceptor pageInterceptor() {
        PageInterceptor pi = new PageInterceptor();
        Properties p = new Properties();
        //当该参数设置为 true 时，pageNum<=0 时会查询第一页， pageNum>pages（超过总数时），会查询最后一页
        p.setProperty("reasonable", "true");
        pi.setProperties(p);
        return pi;
    }

    //mybatis plus分页
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return mybatisPlusInterceptor;
    }

    @Bean
    public DataSource dataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        //添加表
        shardingRuleConfig.getTableRuleConfigs().add(tUserTableRuleConfiguration());
        Properties properties = new Properties();
        //是否开启SQL显示，默认值: false
//        properties.setProperty("sql.show", "true");
        return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig, new HashMap<>(), properties);
    }

    /**
     * 表分片规则配置对象,表：t_user
     * 参考链接：https://shardingsphere.apache.org/document/legacy/3.x/document/cn/manual/sharding-jdbc/configuration/config-java/
     *
     * @return
     */
    TableRuleConfiguration tUserTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration();
        //逻辑表名称
        result.setLogicTable("t_user");
        //由数据源名 + 表名组成，以小数点分隔。多个表以逗号分隔，支持inline表达式。缺省表示使用已知数据源与逻辑表名称生成数据节点。用于广播表（即每个库中都需要一个同样的表用于关联查询，多为字典表）或只分库不分表且所有库的表结构完全一致的情况
        result.setActualDataNodes("ds${0..1}.t_user");
        //分片列名称
        final String shardingColumn = "tags";
        //分片算法行表达式，需符合groovy语法，表达式参考：https://shardingsphere.apache.org/document/legacy/3.x/document/cn/features/sharding/other-features/inline-expression/
        final String algorithmExpression = "ds${tags%2}";
        //ShardingStrategyConfiguration的实现类，用于配置行表达式分片策略。
        result.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration(shardingColumn, algorithmExpression));
        //自增列名称，缺省表示不适用自增主键生成器
//        result.setKeyGeneratorColumnName("id");
        return result;
    }

    Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> result = new HashMap<>();
        result.put("ds0", dataSource_0());
        result.put("ds1", dataSource_1());
        return result;
    }

    /**
     * 数据源-0
     *
     * @return
     */
    public DataSource dataSource_0() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(env.getProperty("ds0.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("ds0.datasource.url"));
        dataSource.setUsername(env.getProperty("ds0.datasource.username"));
        dataSource.setPassword(env.getProperty("ds0.datasource.password"));
        dataSource.setProxyFilters(Lists.newArrayList(statFilter()));
        //每个分区最大的连接数
        dataSource.setMaxActive(20);
        //每个分区最小的连接数
        dataSource.setMinIdle(5);
        return dataSource;
    }

    /**
     * 数据源-1
     *
     * @return
     */
    public DataSource dataSource_1() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(env.getProperty("ds1.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("ds1.datasource.url"));
        dataSource.setUsername(env.getProperty("ds1.datasource.username"));
        dataSource.setPassword(env.getProperty("ds1.datasource.password"));
        dataSource.setProxyFilters(Lists.newArrayList(statFilter()));
        //每个分区最大的连接数
        dataSource.setMaxActive(20);
        //每个分区最小的连接数
        dataSource.setMinIdle(5);
        return dataSource;
    }
}
