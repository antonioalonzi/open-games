package com.aa.opengames.user;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class UserRepository {

  private Map<String, User> users = new HashMap<>();

  public User findByUsername(String username) {
    return users.get(username);
  }

  public void addUser(User user) {
    users.put(user.getUsername(), user);
  }
}
