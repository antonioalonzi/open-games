package com.aa.opengames.authentication.login;

import static com.aa.opengames.authentication.login.LoginResponse.LoginResponseBuilder.loginResponseBuilder;
import static com.aa.opengames.authentication.login.LoginResponse.LoginResponseStatus.ERROR;
import static com.aa.opengames.authentication.login.LoginResponse.LoginResponseStatus.SUCCESS;
import static com.aa.opengames.authentication.login.LoginResponse.UserDetails.UserDetailsBuilder.userDetailsBuilder;
import static com.aa.opengames.event.Event.EventBuilder.eventBuilder;
import static com.aa.opengames.user.User.UserBuilder.userBuilder;

import com.aa.opengames.authentication.context.SecurityContextHolder;
import com.aa.opengames.event.EventSender;
import com.aa.opengames.user.User;
import com.aa.opengames.user.UserRepository;
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
    Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());

    if (!user.isPresent()) {
      User currentUser = userBuilder().username(loginRequest.getUsername()).token(sessionId).build();
      userRepository.addUser(currentUser);
      SecurityContextHolder.addUser(sessionId, currentUser);
      eventSender.sendToUser(sessionId, eventBuilder()
          .type("login-event")
          .value(loginResponseBuilder()
              .setLoginResponseStatus(SUCCESS)
              .setMessage("Login Successful.")
              .setUserDetails(userDetailsBuilder()
                  .token(sessionId)
                  .username(currentUser.getUsername())
                  .build())
              .build())
          .build());

    } else {
      eventSender.sendToUser(sessionId, eventBuilder()
          .type("login-event")
          .value(loginResponseBuilder()
              .setLoginResponseStatus(ERROR)
              .setMessage("Username '" + loginRequest.getUsername() + "' is already used. Please choose another one.")
              .build())
          .build());
    }
  }

}
