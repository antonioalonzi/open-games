package com.aa.opengames.authentication.login;

public class UserLoggedInEvent {
  private String username;

  public String getUsername() {
    return username;
  }

  public UserLoggedInEvent setUsername(String username) {
    this.username = username;
    return this;
  }

  public static class UserConnectedEventBuilder {
    private String username;

    private UserConnectedEventBuilder() {}

    public static UserConnectedEventBuilder userConnectedEventBuilder() {
      return new UserConnectedEventBuilder();
    }

    public UserConnectedEventBuilder username(String username) {
      this.username = username;
      return this;
    }

    public UserLoggedInEvent build() {
      UserLoggedInEvent userLoggedInEvent = new UserLoggedInEvent();
      userLoggedInEvent.setUsername(username);
      return userLoggedInEvent;
    }
  }
}
