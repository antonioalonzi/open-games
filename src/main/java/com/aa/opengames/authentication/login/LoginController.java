package com.aa.opengames.authentication.login;

import static com.aa.opengames.authentication.login.LoginResponse.LoginResponseStatus.ERROR;
import static com.aa.opengames.authentication.login.LoginResponse.LoginResponseStatus.SUCCESS;

import com.aa.opengames.authentication.context.SecurityContextHolder;
import com.aa.opengames.event.EventSender;
import com.aa.opengames.user.User;
import com.aa.opengames.user.UserService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController {

  private final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

  private UserService userService;
  private EventSender eventSender;

  @Autowired
  public LoginController(UserService userService, EventSender eventSender) {
    this.userService = userService;
    this.eventSender = eventSender;
  }

  @MessageMapping("/auth/login")
  public void login(SimpMessageHeaderAccessor headerAccessor, LoginRequest loginRequest) {
    String sessionId = headerAccessor.getSessionId();
    LOGGER.info("Login request received for username '{}'", loginRequest.getUsername());
    Optional<User> user = userService.getUser(loginRequest.getUsername());
    if (user.isPresent()) {
      SecurityContextHolder.addUser(sessionId, user.get());
      eventSender.sendToUser(sessionId, new LoginResponse(SUCCESS, "Login Successful.", sessionId));
    } else {
      eventSender.sendToUser(sessionId, new LoginResponse(ERROR, "Username/Password are incorrect."));
    }
  }

}
