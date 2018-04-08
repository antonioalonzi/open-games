package com.aa.opengames.init;

import static com.aa.opengames.authentication.login.UserLoggedInEvent.UserLoggedInEventBuilder.userLoggedInEventBuilder;
import static com.aa.opengames.event.Event.EventBuilder.eventBuilder;
import static com.aa.opengames.game.GamePublishedEvent.GamePublishedEventBuilder.gamePublishedEventBuilder;
import static com.aa.opengames.table.Table.TableBuilder.tableBuilder;
import static com.aa.opengames.table.TableCreatedEvent.TableCreatedEventBuilder.tableCreatedEventBuilder;
import static com.aa.opengames.user.User.UserBuilder.userBuilder;
import static com.aa.opengames.utils.TestUtils.sessionHeader;
import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.mockito.Mockito.times;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

import com.aa.opengames.event.Event;
import com.aa.opengames.event.EventSender;
import com.aa.opengames.table.Table;
import com.aa.opengames.table.TableCreatedEvent;
import com.aa.opengames.table.TableRepository;
import com.aa.opengames.user.UserRepository;
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
public class InitControllerTest {

  @Autowired
  private InitController initController;

  @Autowired
  private EventSender eventSender;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TableRepository tableRepository;

  private ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);

  @Before
  public void setup() {
    Mockito.reset(eventSender);
    userRepository.removeAllUsers();
    tableRepository.removeAllTables();
  }

  @Test
  public void shouldRespondToInit() {
    // Given
    String username = "user-1";
    String gameLabel = "tic-tac-toe";
    UUID tableId = UUID.randomUUID();
    userRepository.addUser(userBuilder().username(username).build());
    tableRepository.addTable(tableBuilder().id(tableId).game(gameLabel).owner(username).status(Table.Status.NEW).build());
    SimpMessageHeaderAccessor sessionHeader = sessionHeader("sessionId");

    // When
    initController.init(sessionHeader);

    // Then
    Mockito.verify(eventSender, times(3)).sendToUser(argThat(sameBeanAs("sessionId")), eventCaptor.capture());

    assertThat(eventCaptor.getAllValues().get(0), sameBeanAs(eventBuilder()
        .type("user-logged-in")
        .value(userLoggedInEventBuilder().username(username).build())
        .build()));

    assertThat(eventCaptor.getAllValues().get(1), sameBeanAs(eventBuilder()
        .type("game-published")
        .value(gamePublishedEventBuilder()
            .label(gameLabel)
            .name("Tic Tac Toe")
            .description("Simple implementation of Tic Tac Toe.")
            .build())
        .build()));

    assertThat(eventCaptor.getAllValues().get(2), sameBeanAs(eventBuilder()
        .type("table-created")
        .value(tableCreatedEventBuilder()
            .id(tableId)
            .game(gameLabel)
            .owner(username)
            .status(TableCreatedEvent.Status.NEW)
            .build())
        .build()));
  }
}