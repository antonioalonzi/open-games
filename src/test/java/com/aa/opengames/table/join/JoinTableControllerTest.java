package com.aa.opengames.table.join;

import static com.aa.opengames.event.EventResponse.ResponseStatus.ERROR;
import static com.aa.opengames.event.EventResponse.ResponseStatus.SUCCESS;
import static com.aa.opengames.game.tictactoe.TicTacToeGamePlay.TIC_TAC_TOE_LABEL;
import static com.aa.opengames.utils.TestUtils.loginUser;
import static com.aa.opengames.utils.TestUtils.sessionHeader;
import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static java.util.Collections.singleton;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

import com.aa.opengames.authentication.context.SecurityContextHolder;
import com.aa.opengames.event.Event;
import com.aa.opengames.event.EventSender;
import com.aa.opengames.table.Table;
import com.aa.opengames.table.TableRepository;
import com.aa.opengames.table.TableUpdatedEvent;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JoinTableControllerTest {

  @Autowired
  private JoinTableController joinTableController;

  @Autowired
  private EventSender eventSender;

  @Autowired
  private TableRepository tableRepository;

  private ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);

  @Before
  public void setup() {
    Mockito.reset(eventSender);
    tableRepository.removeAllTables();
    SecurityContextHolder.clean();
  }

  @Test(expected = RuntimeException.class)
  public void shouldFailIfNotLoggedIn() {
    // Given
    JoinTableRequest joinTableRequest = JoinTableRequest.builder().build();
    SimpMessageHeaderAccessor sessionHeader = sessionHeader("sessionId");

    // When
    joinTableController.join(sessionHeader, joinTableRequest);
  }

  @Test
  public void shouldJoinATableIfNotAlreadyActiveInOne() {
    // Given
    String username = "user";
    String otherUser = "other-user";
    UUID tableId = UUID.randomUUID();
    JoinTableRequest joinTableRequest = JoinTableRequest.builder().tableId(tableId).build();
    SimpMessageHeaderAccessor sessionHeader = sessionHeader("sessionId");
    loginUser(sessionHeader, username);

    // it works even if the user is already active in a finished table
    tableRepository.addTable(Table.builder()
        .id(UUID.randomUUID())
        .game(TIC_TAC_TOE_LABEL)
        .owner(username)
        .status(Table.Status.FINISHED)
        .build()
    );

    // other-user table to join in
    tableRepository.addTable(Table.builder()
        .id(tableId)
        .game(TIC_TAC_TOE_LABEL)
        .owner(otherUser)
        .status(Table.Status.NEW)
        .build()
    );

    // When
    joinTableController.join(sessionHeader, joinTableRequest);

    // Then
    Event createTableResponse = Event.builder()
        .type(JoinTableResponse.EVENT_TYPE)
        .value(JoinTableResponse.builder()
            .responseStatus(SUCCESS)
            .message("Table successfully joined.")
            .build())
        .build();

    Mockito.verify(eventSender).sendToUser(argThat(sameBeanAs("sessionId")), eventCaptor.capture());
    assertThat(eventCaptor.getValue(), sameBeanAs(createTableResponse));

    Event updatedTableEvent = Event.builder()
        .type(TableUpdatedEvent.EVENT_TYPE)
        .value(TableUpdatedEvent.builder()
            .id(tableId)
            .joiners(singleton(username))
            .status(Table.Status.IN_PROGRESS) // status changes as maxNumPlayer is reached
            .build())
        .build();

    Mockito.verify(eventSender).sendToAll(eventCaptor.capture());
    assertThat(eventCaptor.getValue(), sameBeanAs(updatedTableEvent));
  }

  @Test
  public void shouldReturnAnErrorIfAlreadyActiveInATable() {
    // Given
    String username = "user";
    UUID tableId = UUID.randomUUID();
    JoinTableRequest joinTableRequest = JoinTableRequest.builder().tableId(tableId).build();
    SimpMessageHeaderAccessor sessionHeader = sessionHeader("sessionId");
    loginUser(sessionHeader, username);

    tableRepository.addTable(
        Table.builder()
            .id(tableId)
            .game(TIC_TAC_TOE_LABEL)
            .owner("other-player")
            .joiner(username)
            .status(Table.Status.NEW)
            .build());

    // When
    joinTableController.join(sessionHeader, joinTableRequest);

    // Then
    Event createTableResponse = Event.builder()
        .type(JoinTableResponse.EVENT_TYPE)
        .value(JoinTableResponse.builder()
            .responseStatus(ERROR)
            .message("Cannot join a table as you are already active in the table with id '" + tableId + "' and status 'NEW'.")
            .build())
        .build();

    Mockito.verify(eventSender).sendToUser(argThat(sameBeanAs("sessionId")), eventCaptor.capture());
    assertThat(eventCaptor.getValue(), sameBeanAs(createTableResponse));
  }

}