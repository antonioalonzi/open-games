package com.aa.opengames.authentication.login;

public class LoginResponse {
  private final LoginResponseStatus loginResponseStatus;
  private final String message;
  private final UserDetails userDetails;

  private LoginResponse(LoginResponseStatus loginResponseStatus, String message, UserDetails userDetails) {
    this.loginResponseStatus = loginResponseStatus;
    this.message = message;
    this.userDetails = userDetails;
  }

  public LoginResponseStatus getLoginResponseStatus() {
    return loginResponseStatus;
  }

  public String getMessage() {
    return message;
  }

  public UserDetails getUserDetails() {
    return userDetails;
  }



  public enum LoginResponseStatus {
    SUCCESS, ERROR
  }


  public static class LoginResponseBuilder {
    private LoginResponseStatus loginResponseStatus;
    private String message;
    private UserDetails userDetails;

    private LoginResponseBuilder() {}

    public static LoginResponseBuilder loginResponseBuilder() {
      return new LoginResponseBuilder();
    }

    public LoginResponseBuilder setLoginResponseStatus(LoginResponseStatus loginResponseStatus) {
      this.loginResponseStatus = loginResponseStatus;
      return this;
    }

    public LoginResponseBuilder setMessage(String message) {
      this.message = message;
      return this;
    }

    public LoginResponseBuilder setUserDetails(UserDetails userDetails) {
      this.userDetails = userDetails;
      return this;
    }

    public LoginResponse build() {
      return new LoginResponse(
          loginResponseStatus,
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
