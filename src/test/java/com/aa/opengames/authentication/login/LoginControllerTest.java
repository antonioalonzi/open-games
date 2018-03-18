package com.aa.opengames.authentication.login;

import static com.aa.opengames.authentication.login.LoginResponse.LoginResponseStatus.ERROR;
import static com.aa.opengames.authentication.login.LoginResponse.LoginResponseStatus.SUCCESS;
import static com.aa.opengames.event.Event.EventBuilder.eventBuilder;
import static com.aa.opengames.utils.TestUtils.sessionHeader;
import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

import com.aa.opengames.event.Event;
import com.aa.opengames.event.EventSender;
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

  private ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);

  @Test
  public void shouldLoginSuccessfullyWithCorrectCredentials() {
    // Given
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setUsername("guest");
    loginRequest.setPassword("password");
    SimpMessageHeaderAccessor sessionHeader = sessionHeader("sessionId");

    // When
    loginController.login(sessionHeader, loginRequest);

    // Then
    Event event = eventBuilder()
        .type("login-event")
        .value(new LoginResponse(SUCCESS, "Login Successful.", new LoginResponse.UserDetails("sessionId", "guest")))
        .build();

    Mockito.verify(eventSender).sendToUser(argThat(sameBeanAs("sessionId")), eventCaptor.capture());
    assertThat(eventCaptor.getValue(), sameBeanAs(event));
  }

  @Test
  public void shouldFailToLoginWithWrongPassword() {
    // Given
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setUsername("guest");
    loginRequest.setPassword("wrongPassword");
    SimpMessageHeaderAccessor sessionHeader = sessionHeader("sessionId");

    // When
    loginController.login(sessionHeader, loginRequest);

    // Then
    Event event = eventBuilder()
        .type("login-event")
        .value(new LoginResponse(ERROR, "Username/Password are incorrect."))
        .build();

    Mockito.verify(eventSender).sendToUser(argThat(sameBeanAs("sessionId")), eventCaptor.capture());
    assertThat(eventCaptor.getValue(), sameBeanAs(event));
  }

  @Test
  public void shouldFailToLoginWithNotExistingUser() {
    // Given
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setUsername("notExistingUser");
    loginRequest.setPassword("password");
    SimpMessageHeaderAccessor sessionHeader = sessionHeader("sessionId");

    // When
    loginController.login(sessionHeader, loginRequest);

    // Then
    Event event = eventBuilder()
        .type("login-event")
        .value(new LoginResponse(ERROR, "Username/Password are incorrect."))
        .build();

    Mockito.verify(eventSender).sendToUser(argThat(sameBeanAs("sessionId")), eventCaptor.capture());
    assertThat(eventCaptor.getValue(), sameBeanAs(event));
  }
}