package com.aa.opengames.authentication.login;

import static com.aa.opengames.authentication.login.LoginResponse.LoginResponseStatus.ERROR;
import static com.aa.opengames.authentication.login.LoginResponse.LoginResponseStatus.SUCCESS;
import static com.aa.opengames.event.Event.EventBuilder.eventBuilder;

import com.aa.opengames.authentication.context.SecurityContextHolder;
import com.aa.opengames.event.EventSender;
import com.aa.opengames.user.User;
import com.aa.opengames.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController {

  private final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

  private UserRepository userRepository;
  private EventSender eventSender;

  @Autowired
  public LoginController(UserRepository userRepository, EventSender eventSender) {
    this.userRepository = userRepository;
    this.eventSender = eventSender;
  }

  @MessageMapping("/auth/login")
  public void login(SimpMessageHeaderAccessor headerAccessor, LoginRequest loginRequest) {
    String sessionId = headerAccessor.getSessionId();
    LOGGER.info("Login request received for username '{}'", loginRequest.getUsername());
    User user = userRepository.findByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());

    if (user != null) {
      SecurityContextHolder.addUser(sessionId, user);
      eventSender.sendToUser(sessionId, eventBuilder()
          .type("login-event")
          .value(new LoginResponse(SUCCESS, "Login Successful.", new LoginResponse.UserDetails(sessionId, user.getUsername())))
          .build()
      );

    } else {
      eventSender.sendToUser(sessionId, eventBuilder()
          .type("login-event")
          .value(new LoginResponse(ERROR, "Username/Password are incorrect."))
          .build()
      );
    }
  }

}
