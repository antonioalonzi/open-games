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

  public static class UserLoggedInEventBuilder {
    private String username;

    private UserLoggedInEventBuilder() {}

    public static UserLoggedInEventBuilder userLoggedInEventBuilder() {
      return new UserLoggedInEventBuilder();
    }

    public UserLoggedInEventBuilder username(String username) {
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
