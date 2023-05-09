package com.liwenjie.reggie1.redis;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/5/9 9:22
 */
public class JedisRedisTest {

    @Test
    public void testRedis(){
        // 1. 获取连接
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        // 2. 执行操作
        jedis.set("userNmae", "小明");
        System.out.println(jedis.get("userName"));
        // 3. 关闭连接
        jedis.close();
    }
}
