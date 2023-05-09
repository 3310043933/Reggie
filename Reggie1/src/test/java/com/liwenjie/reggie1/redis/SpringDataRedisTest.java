package com.liwenjie.reggie1.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/5/9 9:49
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringDataRedisTest {

    @Autowired
    public RedisTemplate redisTemplate;

    @Test
    public void stringTest(){
        // 1. 获取对象
        ValueOperations value = redisTemplate.opsForValue();
        value.set("user1", "liwenjie");
    }
}
