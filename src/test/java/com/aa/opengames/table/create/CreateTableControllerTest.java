package com.aa.opengames.table.create;

import static com.aa.opengames.event.EventResponse.ResponseStatus.ERROR;
import static com.aa.opengames.event.EventResponse.ResponseStatus.SUCCESS;
import static com.aa.opengames.game.tictactoe.TicTacToeGamePlay.TIC_TAC_TOE_LABEL;
import static com.aa.opengames.utils.TestUtils.loginUser;
import static com.aa.opengames.utils.TestUtils.sessionHeader;
import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

import com.aa.opengames.authentication.context.SecurityContextHolder;
import com.aa.opengames.event.Event;
import com.aa.opengames.event.EventSender;
import com.aa.opengames.table.Table;
import com.aa.opengames.table.TableRepository;
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
public class CreateTableControllerTest {

  @Autowired
  private CreateTableController createTableController;

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
    CreateTableRequest createTableRequest = CreateTableRequest.builder().build();
    SimpMessageHeaderAccessor sessionHeader = sessionHeader("sessionId");

    // When
    createTableController.create(sessionHeader, createTableRequest);
  }

  @Test
  public void shouldCreateATableForExistingGame() {
    // Given
    String username = "user";
    CreateTableRequest createTableRequest = CreateTableRequest.builder().game(TIC_TAC_TOE_LABEL).build();
    SimpMessageHeaderAccessor sessionHeader = sessionHeader("sessionId");
    loginUser(sessionHeader, username);

    // it works even if the user already owns a finished table
    tableRepository.addTable(Table.builder()
        .id(UUID.randomUUID())
        .game(TIC_TAC_TOE_LABEL)
        .owner(username)
        .status(Table.Status.FINISHED)
        .build()
    );

    // When
    createTableController.create(sessionHeader, createTableRequest);

    // Then
    Event createTableResponse = Event.builder()
        .type(CreateTableResponse.EVENT_TYPE)
        .value(CreateTableResponse.builder()
            .responseStatus(SUCCESS)
            .message("Table successfully created.")
            .build())
        .build();

    Mockito.verify(eventSender).sendToUser(argThat(sameBeanAs("sessionId")), eventCaptor.capture());
    assertThat(eventCaptor.getValue(), sameBeanAs(createTableResponse));

    Event createTableEvent = Event.builder()
        .type(TableCreatedEvent.EVENT_TYPE)
        .value(TableCreatedEvent.builder()
            .id(tableRepository.getActiveTableForUser(username).orElseThrow(() -> new RuntimeException("No active game found")).getId())
            .game(TIC_TAC_TOE_LABEL)
            .owner(username)
            .status(Table.Status.NEW)
            .build())
        .build();

    Mockito.verify(eventSender).sendToAll(eventCaptor.capture());
    assertThat(eventCaptor.getValue(), sameBeanAs(createTableEvent));
  }

  @Test
  public void shouldFailIfUserOwnsAlreadyAGame() {
    // Given
    String user = "user";
    CreateTableRequest createTableRequest = CreateTableRequest.builder().game(TIC_TAC_TOE_LABEL).build();
    SimpMessageHeaderAccessor sessionHeader = sessionHeader("sessionId");
    loginUser(sessionHeader, user);

    UUID tableId = UUID.randomUUID();
    tableRepository.addTable(Table.builder()
        .id(tableId)
        .game(TIC_TAC_TOE_LABEL)
        .owner(user)
        .status(Table.Status.NEW)
        .build()
    );

    // When
    createTableController.create(sessionHeader, createTableRequest);

    // Then
    Event createTableResponse = Event.builder()
        .type(CreateTableResponse.EVENT_TYPE)
        .value(CreateTableResponse.builder()
            .responseStatus(ERROR)
            .message("Cannot create the table as you are already active in the table with id '" + tableId + "' and status 'NEW'.")
            .build())
        .build();

    Mockito.verify(eventSender).sendToUser(argThat(sameBeanAs("sessionId")), eventCaptor.capture());
    assertThat(eventCaptor.getValue(), sameBeanAs(createTableResponse));
  }
}