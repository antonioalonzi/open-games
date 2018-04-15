package com.aa.opengames.table.join;

import static com.aa.opengames.event.EventResponse.ResponseStatus.ERROR;
import static com.aa.opengames.event.EventResponse.ResponseStatus.SUCCESS;

import com.aa.opengames.authentication.context.SecurityContextHolder;
import com.aa.opengames.event.Event;
import com.aa.opengames.event.EventSender;
import com.aa.opengames.game.Game;
import com.aa.opengames.game.GameRepository;
import com.aa.opengames.table.Table;
import com.aa.opengames.table.TableRepository;
import com.aa.opengames.table.TableUpdatedEvent;
import com.aa.opengames.user.User;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class JoinTableController {

  private final Logger LOGGER = LoggerFactory.getLogger(JoinTableController.class);

  private TableRepository tableRepository;
  private GameRepository gameRepository;
  private EventSender eventSender;

  @Autowired
  public JoinTableController(TableRepository tableRepository, GameRepository gameRepository, EventSender eventSender) {
    this.tableRepository = tableRepository;
    this.gameRepository = gameRepository;
    this.eventSender = eventSender;
  }

  @MessageMapping("/table/join")
  public void join(SimpMessageHeaderAccessor headerAccessor, JoinTableRequest joinTableRequest) {
    String token = headerAccessor.getSessionId();
    User user = SecurityContextHolder.getAndCheckUser(token);
    LOGGER.info("Join Table request received from username '{}'", user.getUsername());

    Optional<Table> existingActiveTable = tableRepository.getActiveTableForUser(user.getUsername());

    if (!existingActiveTable.isPresent()) {
      Optional<Table> table = tableRepository.getTableById(joinTableRequest.getTableId());

      if (table.isPresent()) {
        Game game = gameRepository.getGameByLabel(table.get().getGame()).orElseThrow(() -> new RuntimeException("Game with label " + table.get().getGame() + " not found."));

        Set<String> joiners = new HashSet<>(table.get().getJoiners());
        joiners.add(user.getUsername());
        Table updatedTable = table.get().toBuilder()
            .joiners(joiners)
            .build();

        if (updatedTable.getNumOfPlayers() == game.getMaxNumPlayers()) {
          updatedTable = updatedTable.toBuilder().status(Table.Status.IN_PROGRESS).build();
        }

        tableRepository.updateTable(updatedTable);

        eventSender.sendToUser(
            token,
            Event.builder()
                .type(JoinTableResponse.EVENT_TYPE)
                .value(
                    JoinTableResponse.builder()
                        .responseStatus(SUCCESS)
                        .message("Table successfully joined.")
                        .build())
                .build());

        eventSender.sendToAll(
            Event.builder()
                .type(TableUpdatedEvent.EVENT_TYPE)
                .value(
                    TableUpdatedEvent.builder()
                        .id(updatedTable.getId())
                        .status(updatedTable.getStatus())
                        .joiners(updatedTable.getJoiners())
                        .build())
                .build());
      }

    } else {
      eventSender.sendToUser(
          token,
          Event.builder()
              .type(JoinTableResponse.EVENT_TYPE)
              .value(
                  JoinTableResponse.builder()
                      .responseStatus(ERROR)
                      .message("Cannot join a table as you are already active in the table with id '" + existingActiveTable.get().getId() + "' and status '" + existingActiveTable.get().getStatus() + "'.")
                      .build())
              .build());
    }
  }
}
