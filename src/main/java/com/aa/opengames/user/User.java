package com.aa.opengames.user;

public class User {
  private String username;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public static class UserBuilder {
    private String username;

    private UserBuilder() {}

    public static UserBuilder userBuilder() {
      return new UserBuilder();
    }

    public UserBuilder username(String username) {
      this.username = username;
      return this;
    }

    public User build() {
      User user = new User();
      user.setUsername(username);
      return user;
    }
  }
}
