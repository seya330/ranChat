package com.seya330.ranchat.application.user.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginCommand {

  private final String userId;

  private final String password;
}
