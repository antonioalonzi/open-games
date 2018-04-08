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

  public User findByToken(String token) {
    for (String username : users.keySet()) {
      if (users.get(username).getToken().equals(token)) {
        return users.get(username);
      }
    }
    return null;
  }

  public void addUser(User user) {
    users.put(user.getUsername(), user);
  }

  public void removeUser(User user) {
    removeUser(user.getUsername());
  }

  public void removeUser(String username) {
    users.remove(username);
  }
}
