package com.aa.opengames.authentication.logout;

import static com.aa.opengames.utils.TestUtils.sessionHeader;
import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.mockito.BDDMockito.given;

import com.aa.opengames.event.Event;
import com.aa.opengames.event.EventSender;
import com.aa.opengames.user.User;
import com.aa.opengames.user.UserRepository;
import com.aa.opengames.utils.TestUtils;
import java.util.HashMap;
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

    TestUtils.loginUser(sessionHeader(userToken), username);

    SessionDisconnectEvent sessionDisconnectEvent = Mockito.mock(SessionDisconnectEvent.class);
    given(sessionDisconnectEvent.getMessage()).willReturn(new UserDisconnectedMessage(userToken));

    // When
    userDisconnectedListener.onApplicationEvent(sessionDisconnectEvent);

    // Then
    Event loginEvent = Event.builder()
        .type(UserDisconnectedEvent.EVENT_TYPE)
        .value(UserDisconnectedEvent.builder()
            .username(username)
            .build())
        .build();

    Mockito.verify(eventSender).sendToAll(eventCaptor.capture());
    assertThat(eventCaptor.getValue(), sameBeanAs(loginEvent));
  }



  private static class UserDisconnectedMessage implements Message<byte[]> {
    private final String token;

    public UserDisconnectedMessage(String token) {
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