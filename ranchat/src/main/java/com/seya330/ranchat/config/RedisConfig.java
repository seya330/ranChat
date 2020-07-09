package com.seya330.ranchat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seya330.ranchat.user.vo.RegUserVO;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPoolConfig;

@Slf4j
@Configuration
public class RedisConfig{
	@Value("${redis.host}")
	private String redisHost;
	
	@Value("${redis.port}")
	private int redisPort;
	
	@Value("${redis.password}")
	private String redisPassword;
	
	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(30);
		jedisPoolConfig.setMinIdle(10);
		jedisPoolConfig.setTestOnBorrow(true);
		jedisPoolConfig.setTestOnReturn(true);
		return jedisPoolConfig;
	}
	
	@Bean
	public JedisConnectionFactory redisConnectionFactory() {
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(jedisPoolConfig());
		jedisConnectionFactory.setHostName(redisHost);
		jedisConnectionFactory.setPort(redisPort);
		jedisConnectionFactory.setPassword(redisPassword);
		jedisConnectionFactory.setUsePool(true);
		
		return jedisConnectionFactory;
	}
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate(){
		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(redisConnectionFactory());
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(new StringRedisSerializer());
		template.setEnableDefaultSerializer(false);
		template.setEnableTransactionSupport(true);
		return template;
	}
	
	@Bean
	public RedisTemplate<String, RegUserVO> redisUserTemplate(){
		log.info("redisUserTemplate Bean Create");
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(RegUserVO.class);
		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
		
		RedisTemplate<String, RegUserVO> redisTemplate = new RedisTemplate<>();
		
		redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        
        return redisTemplate;
	}
}
