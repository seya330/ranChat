package com.seya330.ranchat.presentation.api.rest.auth.converter;

import com.seya330.ranchat.application.user.command.LoginCommand;
import com.seya330.ranchat.presentation.api.rest.auth.payload.LoginRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoginRequestConverter {

  public LoginRequestConverter INSTANCE = Mappers.getMapper(LoginRequestConverter.class);

  LoginCommand toLoginCommand(LoginRequest loginRequest);
}
