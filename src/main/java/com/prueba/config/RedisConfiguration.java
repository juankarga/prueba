package com.prueba.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.prueba.model.Item;

@Configuration
public class RedisConfiguration {
	
	    @Bean
	    JedisConnectionFactory jedisConnectionFactory() {
	        return new JedisConnectionFactory();
	    }

	    @Bean
	    RedisTemplate<String, Item> redisTemplate() {
	        final RedisTemplate<String, Item> redisTemplate = new RedisTemplate<>();
	        redisTemplate.setConnectionFactory(jedisConnectionFactory());
	        return redisTemplate;
	    }

}
