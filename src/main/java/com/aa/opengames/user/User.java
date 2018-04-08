package com.aa.opengames.user;

import java.util.Objects;

public class User {
  private String username;
  private String token;

  public User(String username, String token) {
    this.username = username;
    this.token = token;
  }

  public String getUsername() {
    return username;
  }

  public String getToken() {
    return token;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(username, user.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username);
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
      return new User(username, token);
    }
  }
}
