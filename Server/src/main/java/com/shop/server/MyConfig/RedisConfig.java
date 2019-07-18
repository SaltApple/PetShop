package com.shop.server.MyConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
        @Bean//操作redis 除了string以外的时用这个模板，否则用下面的
        public <T> RedisTemplate<String,T> redisTemplate(RedisConnectionFactory connectionFactory){
            RedisTemplate redisTemplate=new RedisTemplate();
            redisTemplate.setConnectionFactory(connectionFactory);
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
            return redisTemplate;
        }

        @Bean//操作redis string时用这个模板，否则用上面的
        public <T> RedisTemplate<String, T> redisTemplate1(RedisConnectionFactory collectionFactoryBean){
            RedisTemplate template=new RedisTemplate();
            template.setConnectionFactory(collectionFactoryBean);
            template.setKeySerializer(new StringRedisSerializer());
            template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
            return template;
        }
}
