package com.aa.opengames.authentication.login;

import com.aa.opengames.event.EventResponse;

public class LoginResponse extends EventResponse {

  private final UserDetails userDetails;

  private LoginResponse(ResponseStatus responseStatus, String message, UserDetails userDetails) {
    super(responseStatus, message);
    this.userDetails = userDetails;
  }

  public UserDetails getUserDetails() {
    return userDetails;
  }


  public static class LoginResponseBuilder extends EventResponseBuilder<LoginResponseBuilder> {
    private UserDetails userDetails;

    private LoginResponseBuilder() {}

    public static LoginResponseBuilder loginResponseBuilder() {
      return new LoginResponseBuilder();
    }

    public LoginResponseBuilder userDetails(UserDetails userDetails) {
      this.userDetails = userDetails;
      return this;
    }

    public LoginResponse build() {
      return new LoginResponse(
          responseStatus,
          message,
          userDetails
      );
    }
  }


  public static class UserDetails {
    private final String token;
    private final String username;

    private UserDetails(String token, String username) {
      this.token = token;
      this.username = username;
    }

    public String getToken() {
      return token;
    }

    public String getUsername() {
      return username;
    }

    public static class UserDetailsBuilder {
      private String token;
      private String username;

      private UserDetailsBuilder() {}

      public static UserDetailsBuilder userDetailsBuilder() {
        return new UserDetailsBuilder();
      }

      public UserDetailsBuilder token(String token) {
        this.token = token;
        return this;
      }

      public UserDetailsBuilder username(String username) {
        this.username = username;
        return this;
      }

      public UserDetails build() {
        return new UserDetails(token, username);
      }
    }
  }

}
