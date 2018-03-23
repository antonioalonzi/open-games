package com.aa.opengames.authentication.login;

public class LoginRequest {
  private String username;
  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public static class LoginRequestBuilder {
    private String username;
    private String password;

    private LoginRequestBuilder() {}

    public static LoginRequestBuilder loginRequestBuilder() {
      return new LoginRequestBuilder();
    }

    public LoginRequestBuilder username(String username) {
      this.username = username;
      return this;
    }

    public LoginRequestBuilder password(String password) {
      this.password = password;
      return this;
    }

    public LoginRequest build() {
      LoginRequest loginRequest = new LoginRequest();
      loginRequest.setUsername(username);
      loginRequest.setPassword(password);
      return loginRequest;
    }
  }
}
