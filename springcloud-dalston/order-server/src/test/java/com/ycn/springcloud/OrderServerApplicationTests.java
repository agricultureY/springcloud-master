package com.ycn.springcloud;

import com.ycn.springcloud.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServerApplicationTests {

    @Autowired
    RedisUtil redisUtil;

    @Test
    public void contextLoads() {
        redisUtil.set("test", "test123qweS", 5);
    }

    @Test
    public void testDel() {
        redisUtil.del("test");
    }

}
