package com.aa.opengames.authentication.login;

import com.aa.opengames.authentication.context.SecurityContextHolder;
import com.aa.opengames.user.User;
import com.aa.opengames.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.Optional;

import static com.aa.opengames.authentication.login.LoginResponse.LoginResponseStatus.ERROR;
import static com.aa.opengames.authentication.login.LoginResponse.LoginResponseStatus.SUCCESS;

@Controller
public class LoginController {

  private final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

  @Autowired
  private UserService userService;

  @MessageMapping("/auth/login")
  @SendToUser("/auth/login")
  public LoginResponse login(LoginRequest loginRequest) {
    LOGGER.info("Login request received for username '{}'", loginRequest.getUsername());
    Optional<User> user = userService.getUser(loginRequest.getUsername());
    if (user.isPresent()) {
      String token = SecurityContextHolder.generateNewToken();
      SecurityContextHolder.addUser(token, user.get());
      return new LoginResponse(SUCCESS, "Login Successful.", token);
    }
    return new LoginResponse(ERROR, "Username/Password are incorrect.");
  }

}
