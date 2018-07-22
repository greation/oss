package com.ekeyfund.oss.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.ekeyfund.oss.core.constant.DatasourceEnum;
import com.ekeyfund.oss.core.datascope.DataScopeInterceptor;
import com.ekeyfund.oss.core.datasource.DruidProperties;
import com.ekeyfund.oss.core.mutidatasource.DynamicDataSource;
import com.ekeyfund.oss.core.mutidatasource.config.GYP2PDataSourceProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * MybatisPlus配置
 *
 * @author stylefeng
 * @Date 2017/5/20 21:58
 */
@Configuration
@EnableTransactionManagement(order = 2)//由于引入多数据源，所以让spring事务的aop要在多数据源切换aop的后面
@MapperScan(basePackages = {"com.ekeyfund.oss.modular.*.dao"})
public class MybatisPlusConfig {

    @Autowired
    DruidProperties druidProperties;

    @Autowired
    GYP2PDataSourceProperties gyp2PDataSourceProperties;

    @Value("${guns.defaultDataSourceName}")
    private String defaultDataSourceName = DatasourceEnum.DATA_SOURCE_YQ_OSS.name();

    /**
     * 宜泉数据源
     */
    private DruidDataSource datasourceGYP2P() {
        DruidDataSource dataSource = new DruidDataSource();
        druidProperties.config(dataSource);
        gyp2PDataSourceProperties.config(dataSource);
        return dataSource;
    }

    /**
     * 新oss的数据源
     */
    private DruidDataSource dataSourceYQOSS() {
        DruidDataSource dataSource = new DruidDataSource();
        druidProperties.config(dataSource);
        return dataSource;
    }

    /**
     * 单数据源连接池配置
     */
    @Bean
    @ConditionalOnProperty(prefix = "guns", name = "muti-datasource-open", havingValue = "false")
    public DruidDataSource singleDatasource() {
        return dataSourceYQOSS();
    }

    /**
     * 多数据源连接池配置
     */
    @Bean
    @ConditionalOnProperty(prefix = "guns", name = "muti-datasource-open", havingValue = "true")
    public DynamicDataSource mutiDataSource() {

        DruidDataSource dataSourceGYP2P = datasourceGYP2P();
        DruidDataSource dataSourceYQOSS = dataSourceYQOSS();

        try {
            dataSourceGYP2P.init();
            dataSourceYQOSS.init();
        } catch (SQLException sql) {
            sql.printStackTrace();
        }

        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        HashMap<Object, Object> hashMap = new HashMap();
        hashMap.put(DatasourceEnum.DATA_SOURCE_GY_P2P.name(), dataSourceGYP2P);
        hashMap.put(DatasourceEnum.DATA_SOURCE_YQ_OSS.name(), dataSourceYQOSS);
        dynamicDataSource.setTargetDataSources(hashMap);
        dynamicDataSource.setDefaultTargetDataSource(hashMap.get(defaultDataSourceName));
        return dynamicDataSource;
    }

    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 数据范围mybatis插件
     */
    @Bean
    public DataScopeInterceptor dataScopeInterceptor() {
        return new DataScopeInterceptor();
    }

    /**
     * 乐观锁mybatis插件
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

}
