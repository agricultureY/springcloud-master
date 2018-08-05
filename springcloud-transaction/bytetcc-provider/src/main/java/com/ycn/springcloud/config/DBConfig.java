package com.ycn.springcloud.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.bytesoft.bytejta.supports.jdbc.LocalXADataSource;
import org.bytesoft.bytetcc.supports.springcloud.config.SpringCloudConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

/**
 * bytetcc数据源配置
 *
 * @author ycn
 * @package com.ycn.springcloud.config
 * @ClassName DBConfig
 * @Date 2018/7/12 14:55
 */
@Configuration
@Import(SpringCloudConfiguration.class)
public class DBConfig {

    @Bean(name = "dataSource")
    public DataSource getMybatisDataSource() {
        LocalXADataSource dataSource = new LocalXADataSource();
        dataSource.setDataSource(this.invokeGetDataSource());
        return dataSource;
    }

    public DataSource invokeGetDataSource() {
        BasicDataSource bds = new BasicDataSource();
        bds.setUrl("jdbc:mysql://localhost:3306/test");
        bds.setUsername("root");//用户名
        bds.setPassword("root");//密码
        bds.setMaxTotal(50);
        bds.setInitialSize(20);
        bds.setMaxWaitMillis(60000);
        bds.setMinIdle(6);
        bds.setLogAbandoned(true);
        bds.setRemoveAbandonedOnBorrow(true);
        bds.setRemoveAbandonedOnMaintenance(true);
        bds.setRemoveAbandonedTimeout(1800);
        bds.setTestWhileIdle(true);
        bds.setTestOnBorrow(false);
        bds.setTestOnReturn(false);
        bds.setValidationQuery("select 'x' ");
        bds.setValidationQueryTimeout(1);
        bds.setTimeBetweenEvictionRunsMillis(30000);
        bds.setNumTestsPerEvictionRun(20);
        return bds;
    }

}
