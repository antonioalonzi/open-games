package com.aa.opengames.authentication.login;

public class LoginResponse {
  private final LoginResponseStatus loginResponseStatus;
  private final String message;
  private final UserDetails userDetails;

  public LoginResponse(LoginResponseStatus loginResponseStatus, String message) {
    this(loginResponseStatus, message, null);
  }

  public LoginResponse(LoginResponseStatus loginResponseStatus, String message, UserDetails userDetails) {
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

  public static class UserDetails {
    private String token;
    private String username;

    public UserDetails(String token, String username) {
      this.token = token;
      this.username = username;
    }

    public String getToken() {
      return token;
    }

    public String getUsername() {
      return username;
    }
  }

}
