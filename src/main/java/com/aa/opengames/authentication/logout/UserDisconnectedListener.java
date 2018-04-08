package com.aa.opengames.authentication.logout;

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

  @Autowired
  public UserDisconnectedListener(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void onApplicationEvent(SessionDisconnectEvent event) {
    String token = StompHeaderAccessor.wrap(event.getMessage()).getSessionId();
    userRepository.findByToken(token).ifPresent((user) -> {
       userRepository.removeUser(user);
       LOGGER.info("User Disconnected [username: {}].", user.getUsername());
    });
  }
}

