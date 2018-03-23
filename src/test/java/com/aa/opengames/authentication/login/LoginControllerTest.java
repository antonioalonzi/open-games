package com.aa.opengames.authentication.login;

import static com.aa.opengames.authentication.login.LoginRequest.LoginRequestBuilder.loginRequestBuilder;
import static com.aa.opengames.authentication.login.LoginResponse.LoginResponseBuilder.loginResponseBuilder;
import static com.aa.opengames.authentication.login.LoginResponse.LoginResponseStatus.ERROR;
import static com.aa.opengames.authentication.login.LoginResponse.LoginResponseStatus.SUCCESS;
import static com.aa.opengames.authentication.login.LoginResponse.UserDetails.UserDetailsBuilder.userDetailsBuilder;
import static com.aa.opengames.event.Event.EventBuilder.eventBuilder;
import static com.aa.opengames.utils.TestUtils.sessionHeader;
import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

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
public class LoginControllerTest {

  @Autowired
  private LoginController loginController;

  @Autowired
  private EventSender eventSender;

  private ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);

  @Before
  public void setup() {
    Mockito.reset(eventSender);
  }

  @Test
  public void shouldLoginSuccessfullyWithCorrectCredentials() {
    // Given
    LoginRequest loginRequest = loginRequestBuilder()
        .username("guest")
        .password("password")
        .build();
    SimpMessageHeaderAccessor sessionHeader = sessionHeader("sessionId");

    // When
    loginController.login(sessionHeader, loginRequest);

    // Then
    Event event = eventBuilder()
        .type("login-event")
        .value(loginResponseBuilder()
            .setLoginResponseStatus(SUCCESS)
            .setMessage("Login Successful.")
            .setUserDetails(userDetailsBuilder()
                .token("sessionId")
                .username("guest")
                .build())
            .build())
        .build();

    Mockito.verify(eventSender).sendToUser(argThat(sameBeanAs("sessionId")), eventCaptor.capture());
    assertThat(eventCaptor.getValue(), sameBeanAs(event));
  }

  @Test
  public void shouldFailToLoginWithWrongPassword() {
    // Given
    LoginRequest loginRequest = loginRequestBuilder()
        .username("guest")
        .password("wrong-password")
        .build();
    SimpMessageHeaderAccessor sessionHeader = sessionHeader("sessionId");

    // When
    loginController.login(sessionHeader, loginRequest);

    // Then
    Event event = eventBuilder()
        .type("login-event")
        .value(loginResponseBuilder()
            .setLoginResponseStatus(ERROR)
            .setMessage("Username/Password are incorrect.")
            .build())
        .build();

    Mockito.verify(eventSender).sendToUser(argThat(sameBeanAs("sessionId")), eventCaptor.capture());
    assertThat(eventCaptor.getValue(), sameBeanAs(event));
  }

  @Test
  public void shouldFailToLoginWithNotExistingUser() {
    // Given
    LoginRequest loginRequest = loginRequestBuilder()
        .username("notExistingUser")
        .password("password")
        .build();
    SimpMessageHeaderAccessor sessionHeader = sessionHeader("sessionId");

    // When
    loginController.login(sessionHeader, loginRequest);

    // Then
    Event event = eventBuilder()
        .type("login-event")
        .value(loginResponseBuilder()
            .setLoginResponseStatus(ERROR)
            .setMessage("Username/Password are incorrect.")
            .build())
        .build();

    Mockito.verify(eventSender).sendToUser(argThat(sameBeanAs("sessionId")), eventCaptor.capture());
    assertThat(eventCaptor.getValue(), sameBeanAs(event));
  }
}