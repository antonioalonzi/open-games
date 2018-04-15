package com.aa.opengames.authentication.login;

import static com.aa.opengames.event.EventResponse.ResponseStatus.ERROR;
import static com.aa.opengames.event.EventResponse.ResponseStatus.SUCCESS;
import static com.aa.opengames.utils.TestUtils.sessionHeader;
import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

import com.aa.opengames.event.Event;
import com.aa.opengames.event.EventSender;
import com.aa.opengames.user.UserRepository;
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
public class LoginControllerTest {

  @Autowired
  private LoginController loginController;

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
  public void shouldLoginSuccessfullyAndSendLoggedInEvent() {
    // Given
    String username = "user-1";

    LoginRequest loginRequest = LoginRequest.builder()
        .username(username)
        .build();
    SimpMessageHeaderAccessor sessionHeader = sessionHeader("sessionId");

    // When
    loginController.login(sessionHeader, loginRequest);

    // Then
    Event loginEvent = Event.builder()
        .type(LoginResponse.EVENT_TYPE)
        .value(LoginResponse.builder()
            .responseStatus(SUCCESS)
            .message("Login Successful.")
            .userDetails(LoginResponse.UserDetails.builder()
                .token("sessionId")
                .username(username)
                .build())
            .build())
        .build();

    Mockito.verify(eventSender).sendToUser(argThat(sameBeanAs("sessionId")), eventCaptor.capture());
    assertThat(eventCaptor.getValue(), sameBeanAs(loginEvent));

    Event userLoggedInEvent = Event.builder()
        .type(UserLoggedInEvent.EVENT_TYPE)
        .value(UserLoggedInEvent.builder().username(username).build())
        .build();

    Mockito.verify(eventSender).sendToAll(eventCaptor.capture());
    assertThat(eventCaptor.getValue(), sameBeanAs(userLoggedInEvent));
  }

  @Test
  public void shouldThrowAnErrorIfAnUserWithSameUsernameIsConnected() {
    // Given
    String username = "user-1";

    LoginRequest loginRequest = LoginRequest.builder()
        .username(username)
        .build();
    SimpMessageHeaderAccessor sessionHeader = sessionHeader("sessionId");

    loginController.login(sessionHeader, loginRequest);
    Mockito.reset(eventSender);

    // When
    loginController.login(sessionHeader, loginRequest);

    // Then
    Event loginEvent = Event.builder()
        .type(LoginResponse.EVENT_TYPE)
        .value(LoginResponse.builder()
            .responseStatus(ERROR)
            .message("Username '" + username + "' is already used. Please choose another one." )
            .build())
        .build();

    Mockito.verify(eventSender).sendToUser(argThat(sameBeanAs("sessionId")), eventCaptor.capture());
    assertThat(eventCaptor.getValue(), sameBeanAs(loginEvent));
  }
}