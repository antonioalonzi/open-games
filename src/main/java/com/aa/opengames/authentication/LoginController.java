package com.aa.opengames.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import static com.aa.opengames.authentication.LoginResponse.LoginResponseStatus.SUCCESS;

@Controller
public class LoginController {

  private final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

  @MessageMapping("/auth/login")
  @SendTo("/messages")
  public LoginResponse login(LoginRequest loginRequest) {
    LOGGER.info("Login request received for username '{}'", loginRequest.getUsername());
    return new LoginResponse(SUCCESS, "Login Successful");
  }

}
