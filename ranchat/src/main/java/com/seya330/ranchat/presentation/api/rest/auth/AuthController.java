package com.seya330.ranchat.presentation.api.rest.auth;

import javax.servlet.http.HttpServletResponse;

import com.seya330.ranchat.presentation.api.rest.auth.converter.LoginRequestConverter;
import com.seya330.ranchat.presentation.api.rest.auth.payload.LoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.seya330.ranchat.application.user.service.RegUserApplicationService;
import com.seya330.ranchat.core.user.vo.LoginResultType;
import com.seya330.ranchat.core.user.vo.RegUserVO;

@RestController
public class AuthController {
  private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
  @Autowired
  RegUserApplicationService userApplicationService;

  @CrossOrigin
  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginRequest request) {
    HttpStatus result = HttpStatus.OK;
    String resultStr = "";

    String token = userApplicationService.getToken(LoginRequestConverter.INSTANCE
        .toLoginCommand(request));

    return ResponseEntity.status(result).body(token);
  }

  @GetMapping("/login2")
  public Object login2(RegUserVO userVO, HttpServletResponse response) {
    logger.info("login request user: " + userVO.getUserId());
    HttpStatus result = HttpStatus.OK;
    String resultStr = "";
    RegUserVO user = userApplicationService.getToken(userVO);

    if (user.getLoginResultType() != LoginResultType.SUCCESS) {
      result = HttpStatus.UNAUTHORIZED;
      resultStr = user.getLoginResultType().toString();
    } else {
      resultStr = user.getToken();
    }

    return ResponseEntity.status(result).body(resultStr);
  }
}
