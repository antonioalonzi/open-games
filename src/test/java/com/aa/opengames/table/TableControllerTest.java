package com.aa.opengames.table;

import static com.aa.opengames.authentication.login.LoginResponse.LoginResponseBuilder.loginResponseBuilder;
import static com.aa.opengames.event.Event.EventBuilder.eventBuilder;
import static com.aa.opengames.event.EventResponse.ResponseStatus.SUCCESS;
import static com.aa.opengames.table.CreateTableRequest.CreateTableRequestBuilder.createTableRequestBuilder;
import static com.aa.opengames.table.TableCreatedEvent.TableCreatedEventBuilder.tableCreatedEventBuilder;
import static com.aa.opengames.utils.TestUtils.loginUser;
import static com.aa.opengames.utils.TestUtils.sessionHeader;
import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

import com.aa.opengames.authentication.context.SecurityContextHolder;
import com.aa.opengames.event.Event;
import com.aa.opengames.event.EventSender;
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
public class TableControllerTest {

  @Autowired
  private TableController tableController;

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
    CreateTableRequest createTableRequest = createTableRequestBuilder().build();
    SimpMessageHeaderAccessor sessionHeader = sessionHeader("sessionId");

    // When
    tableController.create(sessionHeader, createTableRequest);
  }

  @Test
  public void shouldCreateATableForExistingGame() {
    // Given
    CreateTableRequest createTableRequest = createTableRequestBuilder().game("tic-tac-toe").build();
    SimpMessageHeaderAccessor sessionHeader = sessionHeader("sessionId");
    loginUser(sessionHeader, "user");

    // When
    tableController.create(sessionHeader, createTableRequest);

    // Then
    Event createTableResponse = eventBuilder()
        .type("create-table-response")
        .value(loginResponseBuilder()
            .responseStatus(SUCCESS)
            .message("Table successfully created.")
            .build())
        .build();

    Mockito.verify(eventSender).sendToUser(argThat(sameBeanAs("sessionId")), eventCaptor.capture());
    assertThat(eventCaptor.getValue(), sameBeanAs(createTableResponse));

    Event createTableEvent = eventBuilder()
        .type("table-created-event")
        .value(tableCreatedEventBuilder()
            .id(tableRepository.getAllTables().iterator().next().getId())
            .game("tic-tac-toe")
            .owner("user")
            .status(TableCreatedEvent.Status.NEW)
            .build())
        .build();

    Mockito.verify(eventSender).sendToAll(eventCaptor.capture());
    assertThat(eventCaptor.getValue(), sameBeanAs(createTableEvent));
  }

}