package com.aa.opengames.table.leave;

import com.aa.opengames.authentication.context.SecurityContextHolder;
import com.aa.opengames.event.Event;
import com.aa.opengames.event.EventSender;
import com.aa.opengames.game.Game;
import com.aa.opengames.game.GameRepository;
import com.aa.opengames.game.play.GamePlayFactory;
import com.aa.opengames.table.Table;
import com.aa.opengames.table.TableRepository;
import com.aa.opengames.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Optional;

import static com.aa.opengames.event.EventResponse.ResponseStatus.ERROR;
import static com.aa.opengames.event.EventResponse.ResponseStatus.SUCCESS;

@Controller
public class LeaveTableController {

    private final Logger LOGGER = LoggerFactory.getLogger(LeaveTableController.class);

    private final TableRepository tableRepository;
    private final GameRepository gameRepository;
    private final EventSender eventSender;
    private final GamePlayFactory gamePlayFactory;

    @Autowired
    public LeaveTableController(TableRepository tableRepository, GameRepository gameRepository, EventSender eventSender, GamePlayFactory gamePlayFactory) {
        this.tableRepository = tableRepository;
        this.gameRepository = gameRepository;
        this.eventSender = eventSender;
        this.gamePlayFactory = gamePlayFactory;
    }

    @MessageMapping("/table/leave")
    public void leave(SimpMessageHeaderAccessor headerAccessor, LeaveTableRequest leaveTableRequest) {
        String token = headerAccessor.getSessionId();
        User user = SecurityContextHolder.getAndCheckUser(token);
        LOGGER.info("Leave Table request received from username '{}' for table '{}'", user.getUsername(), leaveTableRequest.getTableId());

        Optional<Table> existingActiveTable = tableRepository.getActiveTableForUser(user.getUsername());

        if (existingActiveTable.isPresent()) {
            Table table = tableRepository.getTableById(leaveTableRequest.getTableId());

            Game game = gameRepository.getGameByLabel(table.getGame()).orElseThrow(() -> new RuntimeException("Game with label " + table.getGame() + " not found."));

            eventSender.sendToUser(
                    token,
                    Event.builder()
                            .type(LeaveTableResponse.EVENT_TYPE)
                            .value(LeaveTableResponse.builder()
                                    .responseStatus(SUCCESS)
                                    .message("Table successfully left.")
                                    .build())
                            .build());



            // TODO Update the table and the gamePlay(tic tac toe) as FINISHED on the database
            // TODO send Table finished event to all users

        } else {
            eventSender.sendToUser(
                    token,
                    Event.builder()
                            .type(LeaveTableResponse.EVENT_TYPE)
                            .value(LeaveTableResponse.builder()
                                    .responseStatus(ERROR)
                                    .message("Cannot leave the table with id '" + leaveTableRequest.getTableId() + "' as you are not active in it.")
                                    .build())
                            .build());
        }
    }
}
