package com.seya330.ranchat.core.user.repository;

import com.seya330.ranchat.core.user.domain.RegUser;
import com.seya330.ranchat.core.user.domain.UserStatus;
import com.seya330.ranchat.share.util.IDGeneratorUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

@DataJpaTest
class RegUserRepositoryTest {

  @Autowired
  RegUserRepository regUserRepository;

  @BeforeEach
  void init() {
    RegUser regUser = RegUser.builder()
        .uniqId(IDGeneratorUtil.generateId("M", 19))
        .userId("tempuser")
        .userStatus(UserStatus.U)
        .regDate(LocalDateTime.now())
        .modDate(LocalDateTime.now())
        .password("temppassword")
        .build();
    regUserRepository.save(regUser);
  }

  @Test
  void findByUserIdAndUserStatus() {
    RegUser user = regUserRepository.findByUserIdAndUserStatus("tempuser", UserStatus.U).get();
    user.getUserId();
  }
}