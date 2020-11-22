package com.seya330.ranchat.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(value = ExpiredJwtException.class)
	public ResponseEntity handleBaseException(HttpServletRequest request, ExpiredJwtException e) {
		return ResponseEntity.status(401).body("EXPIRE TOKEN");
	}
}
