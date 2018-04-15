package com.aa.opengames.init;

import static com.aa.opengames.utils.TestUtils.sessionHeader;
import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.mockito.Mockito.times;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

import com.aa.opengames.authentication.login.UserLoggedInEvent;
import com.aa.opengames.event.Event;
import com.aa.opengames.event.EventSender;
import com.aa.opengames.game.GamePublishedEvent;
import com.aa.opengames.table.Table;
import com.aa.opengames.table.create.TableCreatedEvent;
import com.aa.opengames.table.TableRepository;
import com.aa.opengames.user.User;
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
    userRepository.addUser(User.builder().username(username).build());
    UUID table1Id = UUID.randomUUID();
    tableRepository.addTable(Table.builder().id(table1Id).game(gameLabel).owner(username).status(Table.Status.NEW).build());
    UUID table2Id = UUID.randomUUID();
    tableRepository.addTable(Table.builder().id(table2Id).game(gameLabel).owner("another-user").joiner("a-joiner").status(Table.Status.FINISHED).build());
    SimpMessageHeaderAccessor sessionHeader = sessionHeader("sessionId");

    // When
    initController.init(sessionHeader);

    // Then
    Mockito.verify(eventSender, times(4)).sendToUser(argThat(sameBeanAs("sessionId")), eventCaptor.capture());

    assertThat(
        eventCaptor.getAllValues().get(0),
        sameBeanAs(
            Event.builder()
                .type(UserLoggedInEvent.EVENT_TYPE)
                .value(UserLoggedInEvent.builder().username(username).build())
                .build()));

    assertThat(eventCaptor.getAllValues().get(1), sameBeanAs(Event.builder()
        .type(GamePublishedEvent.EVENT_TYPE)
        .value(GamePublishedEvent.builder()
            .label(gameLabel)
            .name("Tic Tac Toe")
            .description("Simple implementation of Tic Tac Toe.")
            .minNumPlayers(2)
            .maxNumPlayers(2)
            .build())
        .build()));

    assertThat(eventCaptor.getAllValues().get(2), sameBeanAs(Event.builder()
        .type(TableCreatedEvent.EVENT_TYPE)
        .value(TableCreatedEvent.builder()
            .id(table1Id)
            .game(gameLabel)
            .owner(username)
            .status(Table.Status.NEW)
            .build())
        .build()));

    assertThat(eventCaptor.getAllValues().get(3), sameBeanAs(Event.builder()
        .type(TableCreatedEvent.EVENT_TYPE)
        .value(TableCreatedEvent.builder()
            .id(table2Id)
            .game(gameLabel)
            .owner("another-user")
            .status(Table.Status.FINISHED)
            .joiner("a-joiner")
            .build())
        .build()));
  }
}