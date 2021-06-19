package com.seya330.ranchat.presentation.api.rest.auth.payload;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequest {

  private final String userId;

  private final String password;
}
