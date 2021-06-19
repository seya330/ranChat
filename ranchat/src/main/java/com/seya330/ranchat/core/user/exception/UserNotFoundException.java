package com.seya330.ranchat.core.user.exception;

public class UserNotFoundException extends RuntimeException{
  public UserNotFoundException() {
    super("User Not Found");
  }
}
