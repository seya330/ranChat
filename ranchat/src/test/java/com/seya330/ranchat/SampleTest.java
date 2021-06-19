package com.seya330.ranchat;

import com.seya330.ranchat.core.user.vo.RegUserVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
public class SampleTest {

  @Autowired
  private RedisTemplate redisTemplate;

  @Test
  void name() {
    ValueOperations<String, RegUserVO> redisLoginOperation = redisTemplate.opsForValue();
    RegUserVO vo = new RegUserVO();
    vo.setUserId("1345");
    vo.setPassword("asdfasdf");
    redisLoginOperation.set("12345", vo);
  }
}
