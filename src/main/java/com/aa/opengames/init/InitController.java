package com.aa.opengames.init;

import static com.aa.opengames.authentication.login.UserLoggedInEvent.UserConnectedEventBuilder.userConnectedEventBuilder;
import static com.aa.opengames.event.Event.EventBuilder.eventBuilder;

import com.aa.opengames.event.EventSender;
import com.aa.opengames.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class InitController {

  private final Logger LOGGER = LoggerFactory.getLogger(InitController.class);

  private UserRepository userRepository;
  private EventSender eventSender;

  @Autowired
  public InitController(UserRepository userRepository, EventSender eventSender) {
    this.userRepository = userRepository;
    this.eventSender = eventSender;
  }

  @MessageMapping("/init")
  public void init(SimpMessageHeaderAccessor headerAccessor) {
    String sessionId = headerAccessor.getSessionId();
    LOGGER.info("Init request received for sessionId '{}'", sessionId);

    userRepository.getAllUsers().forEach((user) -> eventSender.sendToUser(sessionId, eventBuilder()
        .type("user-logged-in")
        .value(userConnectedEventBuilder()
            .username(user.getUsername())
            .build())
        .build()));
  }

}
