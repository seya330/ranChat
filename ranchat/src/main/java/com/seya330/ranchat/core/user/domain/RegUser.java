package com.seya330.ranchat.core.user.domain;

import com.seya330.ranchat.core.user.exception.InvalidPasswordException;
import com.seya330.ranchat.share.util.UserUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegUser {

  @Id
  @NotNull
  private String uniqId;

  @NotNull
  private String userId;

  @NotNull
  private String password;

  private LocalDateTime regDate;

  private LocalDateTime modDate;

  @Enumerated(EnumType.STRING)
  private UserStatus userStatus;

  public void validatePassword(String password) {
    if(!UserUtil.getSha256String(password).equals(this.password))
      throw new InvalidPasswordException();
  }
}
