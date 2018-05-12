package com.aa.opengames.event;

import com.aa.opengames.user.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Sends events to the browser.
 */
@Component
public class EventSender {

  private static final Logger LOGGER = LoggerFactory.getLogger(EventSender.class);

  private final SimpMessagingTemplate webSocketTemplate;
  private final ObjectMapper objectMapper;
  private final UserRepository userRepository;

  @Autowired
  public EventSender(SimpMessagingTemplate simpleMessagingTemplate, ObjectMapper objectMapper, UserRepository userRepository) {
    this.webSocketTemplate = simpleMessagingTemplate;
    this.objectMapper = objectMapper;
    this.userRepository = userRepository;
  }

  public void sendToAll(Event event) {
    String eventString = serializeToString(event);
    LOGGER.info("Sending event to all: {}", eventString);
    webSocketTemplate.convertAndSend("/topic/events", eventString);
  }

  public void sendToUser(String sessionId, Event event) {
    String eventString = serializeToString(event);
    LOGGER.info("Sending event to {}: {}", sessionId, eventString);
    webSocketTemplate.convertAndSendToUser(sessionId, "/events", eventString);
  }

  public void sendToUsers(List<String> usernames, Event event) {
    userRepository.getAllUsers().stream()
            .filter((user) -> usernames.contains(user.getUsername()))
            .forEach((user) -> sendToUser(user.getToken(), event));
  }

  private String serializeToString(Object event) {
    try {
      return objectMapper.writeValueAsString(event);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
