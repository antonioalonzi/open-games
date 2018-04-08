package com.aa.opengames.user;

public class User {
  private String username;
  private String token;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getToken() {
    return token;
  }

  public User setToken(String token) {
    this.token = token;
    return this;
  }

  public static class UserBuilder {
    private String username;
    private String token;

    private UserBuilder() {}

    public static UserBuilder userBuilder() {
      return new UserBuilder();
    }

    public UserBuilder username(String username) {
      this.username = username;
      return this;
    }

    public UserBuilder token(String token) {
      this.token = token;
      return this;
    }

    public User build() {
      User user = new User();
      user.setUsername(username);
      user.setToken(token);
      return user;
    }
  }
}
