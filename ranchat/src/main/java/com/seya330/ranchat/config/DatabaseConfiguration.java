package com.seya330.ranchat.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
	@Value("${spring.datasource.driver}")
	String driverClassName;
	
	@Value("${spring.datasource.url}")
	String jdbcUrl;
	
	@Value("${spring.datasource.username}")
	String userName;
	
	@Value("${spring.datasource.password}")
	String password;
	
	@Bean
	public DataSource dataSource() {
		HikariConfig conf = new HikariConfig();
		//conf.setDriverClassName("org.mariadb.jdbc.Driver");
		conf.setDriverClassName(driverClassName);
		conf.setJdbcUrl(jdbcUrl);
		conf.setUsername(userName);
		conf.setPassword(password);
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
