package com.aa.opengames.init;

import static com.aa.opengames.authentication.login.UserLoggedInEvent.UserLoggedInEventBuilder.userLoggedInEventBuilder;
import static com.aa.opengames.event.Event.EventBuilder.eventBuilder;
import static com.aa.opengames.game.GamePublishedEvent.GamePublishedEventBuilder.gamePublishedEventBuilder;
import static com.aa.opengames.table.TableCreatedEvent.TableCreatedEventBuilder.tableCreatedEventBuilder;

import com.aa.opengames.event.EventSender;
import com.aa.opengames.game.GameRepository;
import com.aa.opengames.table.TableRepository;
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
  private GameRepository gameRepository;
  private TableRepository tableRepository;
  private EventSender eventSender;

  @Autowired
  public InitController(UserRepository userRepository, GameRepository gameRepository, TableRepository tableRepository, EventSender eventSender) {
    this.userRepository = userRepository;
    this.gameRepository = gameRepository;
    this.tableRepository = tableRepository;
    this.eventSender = eventSender;
  }

  @MessageMapping("/init")
  public void init(SimpMessageHeaderAccessor headerAccessor) {
    String sessionId = headerAccessor.getSessionId();
    LOGGER.info("Init request received for sessionId '{}'", sessionId);

    userRepository.getAllUsers().forEach((user) -> eventSender.sendToUser(sessionId, eventBuilder()
        .type("user-logged-in")
        .value(userLoggedInEventBuilder()
            .username(user.getUsername())
            .build())
        .build()));

    gameRepository.getAllGames().forEach((game) -> eventSender.sendToUser(sessionId, eventBuilder()
        .type("game-published")
        .value(gamePublishedEventBuilder()
            .label(game.getLabel())
            .name(game.getName())
            .description(game.getDescription())
            .build())
        .build()));

    tableRepository.getAllTables().forEach((table) -> eventSender.sendToUser(sessionId, eventBuilder()
        .type("table-created")
        .value(tableCreatedEventBuilder()
            .id(table.getId())
            .game(table.getGame())
            .owner(table.getOwner())
            .status(table.getStatus())
            .build())
        .build()));
  }
}
