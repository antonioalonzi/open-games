package com.aa.opengames.user;

import org.springframework.data.annotation.Id;

public class User {
  @Id
  public String id;
  private String username;

  public User(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }
}
