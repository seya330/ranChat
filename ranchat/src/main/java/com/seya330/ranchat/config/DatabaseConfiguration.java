package com.seya330.ranchat.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
//@MapperScan(value= {""}, sqlSessionFactoryRef="")
public class DatabaseConfiguration {
	@Bean
	public DataSource dataSource() {
		HikariConfig conf = new HikariConfig();
		//conf.setDriverClassName("org.mariadb.jdbc.Driver");
		conf.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
		conf.setJdbcUrl("jdbc:log4jdbc:mariadb://ec2-13-125-149-76.ap-northeast-2.compute.amazonaws.com:3306/ranchat?characterEncoding=utf8");
		conf.setUsername("ranchat");
		conf.setPassword("R@nchat");
		return new HikariDataSource(conf);
	}
	
	@Primary
	@Bean(name="defaultFactory")
	public SqlSessionFactory defaultFactory(ApplicationContext context) throws Exception{
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean.setMapperLocations(context.getResources("classpath:mapper/com/seya330/ranchat/**/*.xml"));
		
		/*
		 * 작동이안됨 Properties properties = new Properties();
		 * properties.setProperty("mapUnderscoreToCamelCase", "true");
		 * factoryBean.setConfigurationProperties(properties);
		 */
		
		Resource resource = new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml"); 
		factoryBean.setConfigLocation(resource);
		
		return factoryBean.getObject();
	}
	
	@Primary
	@Bean(name="defaultSqlSession")
	public SqlSessionTemplate defaultSqlSession(@Qualifier("defaultFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
