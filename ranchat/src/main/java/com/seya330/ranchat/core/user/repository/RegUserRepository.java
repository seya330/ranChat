package com.seya330.ranchat.core.user.repository;

import com.seya330.ranchat.core.user.domain.RegUser;
import com.seya330.ranchat.core.user.domain.UserStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RegUserRepository extends CrudRepository<RegUser, String> {

  Optional<RegUser> findByUserIdAndUserStatus(String userId, UserStatus userStatus);
}
