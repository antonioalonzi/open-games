package com.aa.opengames.table;

import static com.aa.opengames.event.EventResponse.ResponseStatus.SUCCESS;

import com.aa.opengames.authentication.context.SecurityContextHolder;
import com.aa.opengames.event.Event;
import com.aa.opengames.event.EventSender;
import com.aa.opengames.user.User;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class TableController {

  private final Logger LOGGER = LoggerFactory.getLogger(TableController.class);

  private TableRepository tableRepository;
  private EventSender eventSender;

  @Autowired
  public TableController(TableRepository tableRepository, EventSender eventSender) {
    this.tableRepository = tableRepository;
    this.eventSender = eventSender;
  }

  @MessageMapping("/table")
  public void create(SimpMessageHeaderAccessor headerAccessor, CreateTableRequest createTableRequest) {
    String token = headerAccessor.getSessionId();
    User user = SecurityContextHolder.getAndCheckUser(token);
    LOGGER.info("Create Table request received from username '{}'", user.getUsername());

    Table table = Table.builder()
        .id(UUID.randomUUID())
        .game(createTableRequest.getGame())
        .owner(user.getUsername())
        .status(Table.Status.NEW)
        .build();

    tableRepository.addTable(table);
    eventSender.sendToUser(token, Event.builder()
        .type("create-table-response")
        .value(CreateTableResponse.builder()
            .responseStatus(SUCCESS)
            .message("Table successfully created.")
            .build())
        .build());

    eventSender.sendToAll(Event.builder()
        .type("table-created-event")
        .value(TableCreatedEvent.builder()
            .id(table.getId())
            .game(table.getGame())
            .owner(table.getOwner())
            .status(table.getStatus())
            .build())
        .build()
    );
  }

}
