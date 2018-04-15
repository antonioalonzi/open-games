package com.aa.opengames.authentication.logout;

import com.aa.opengames.event.Event;
import com.aa.opengames.event.EventSender;
import com.aa.opengames.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class UserDisconnectedListener implements ApplicationListener<SessionDisconnectEvent> {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserDisconnectedListener.class);

  private UserRepository userRepository;
  private EventSender eventSender;

  @Autowired
  public UserDisconnectedListener(UserRepository userRepository, EventSender eventSender) {
    this.userRepository = userRepository;
    this.eventSender = eventSender;
  }

  public void onApplicationEvent(SessionDisconnectEvent event) {
    String token = StompHeaderAccessor.wrap(event.getMessage()).getSessionId();
    userRepository.findByToken(token).ifPresent((user) -> {
       eventSender.sendToAll(Event.builder()
           .type(UserDisconnectedEvent.EVENT_TYPE)
           .value(UserDisconnectedEvent.builder().username(user.getUsername()).build())
           .build()
       );

      userRepository.removeUser(user);
      LOGGER.info("User Disconnected [username: {}].", user.getUsername());
    });
  }
}

