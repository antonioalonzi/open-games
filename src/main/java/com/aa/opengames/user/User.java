package com.aa.opengames.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {
  @Id
  private String id;
  @Indexed(unique = true)
  private String username;
  private String password;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

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

  public static class UserBuilder {
    private String id;
    private String username;
    private String password;

    private UserBuilder() {}

    public static UserBuilder userBuilder() {
      return new UserBuilder();
    }

    public UserBuilder id(String id) {
      this.id = id;
      return this;
    }

    public UserBuilder username(String username) {
      this.username = username;
      return this;
    }


    public UserBuilder password(String password) {
      this.password = password;
      return this;
    }

    public User build() {
      User user = new User();
      user.setId(id);
      user.setUsername(username);
      user.setPassword(password);
      return user;
    }
  }
}
