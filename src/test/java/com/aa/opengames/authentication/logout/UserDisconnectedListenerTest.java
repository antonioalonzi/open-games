package com.aa.opengames.authentication.logout;

import static com.aa.opengames.game.tictactoe.TicTacToeGamePlay.TIC_TAC_TOE_LABEL;
import static com.aa.opengames.utils.TestUtils.sessionHeader;
import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;

import com.aa.opengames.event.Event;
import com.aa.opengames.event.EventSender;
import com.aa.opengames.table.Table;
import com.aa.opengames.table.TableRepository;
import com.aa.opengames.table.TableUpdatedEvent;
import com.aa.opengames.user.User;
import com.aa.opengames.user.UserRepository;
import com.aa.opengames.utils.TestUtils;
import java.util.HashMap;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserDisconnectedListenerTest {

  @Autowired
  private UserDisconnectedListener userDisconnectedListener;

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
  }

  @Test
  public void shouldDisconnectAConnectedUserAndCancelItsStuff() {
    // Given
    String username = "user-1";
    String userToken = "user-token";
    userRepository.addUser(User.builder()
        .username(username)
        .token(userToken)
        .build()
    );

    UUID newTableId = UUID.randomUUID();
    tableRepository.addTable(Table.builder()
        .id(newTableId)
        .game(TIC_TAC_TOE_LABEL)
        .status(Table.Status.NEW)
        .owner(username)
        .build());

    tableRepository.addTable(Table.builder()
        .id(UUID.randomUUID())
        .game(TIC_TAC_TOE_LABEL)
        .status(Table.Status.FINISHED)
        .owner(username)
        .build());

    TestUtils.loginUser(sessionHeader(userToken), username);

    SessionDisconnectEvent sessionDisconnectEvent = Mockito.mock(SessionDisconnectEvent.class);
    given(sessionDisconnectEvent.getMessage()).willReturn(new UserDisconnectedMessage(userToken));

    // When
    userDisconnectedListener.onApplicationEvent(sessionDisconnectEvent);

    // Then
    Mockito.verify(eventSender, times(2)).sendToAll(eventCaptor.capture());

    // an user-disconnected-event is sent for that user
    Event loginEvent = Event.builder()
        .type(UserDisconnectedEvent.EVENT_TYPE)
        .value(UserDisconnectedEvent.builder()
            .username(username)
            .build())
        .build();
    assertThat(eventCaptor.getAllValues().get(0), sameBeanAs(loginEvent));

    // a table-updated-event with status cancelled is sent if owned a table NEW or IN_PROGRESS
    Event tableUpdatedEvent = Event.builder()
        .type(TableUpdatedEvent.EVENT_TYPE)
        .value(TableUpdatedEvent.builder()
            .id(newTableId)
            .status(Table.Status.CANCELLED)
            .build())
        .build();

    assertThat(eventCaptor.getAllValues().get(1), sameBeanAs(tableUpdatedEvent));

    // table is cancelled in the repository too
    Table updatedTable = tableRepository.getTableById(newTableId);
    assertThat(updatedTable.getStatus(), sameBeanAs(Table.Status.CANCELLED));
  }



  private static class UserDisconnectedMessage implements Message<byte[]> {
    private final String token;

    UserDisconnectedMessage(String token) {
      this.token = token;
    }

    @Override
    public byte[] getPayload() {
      return new byte[0];
    }

    @Override
    public MessageHeaders getHeaders() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put("simpSessionId", token);
        return new MessageHeaders(headers);
    }
  }
}