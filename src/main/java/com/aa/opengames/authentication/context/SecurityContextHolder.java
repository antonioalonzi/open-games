package com.aa.opengames.authentication.context;

import com.aa.opengames.user.User;
import java.util.HashMap;

public class SecurityContextHolder {

  private static HashMap<String, User> users = new HashMap<>();

  public static void addUser(String token, User user) {
    users.put(token, user);
  }

  public static User getAndCheckUser(String token) {
    if (users.containsKey(token)) {
      return users.get(token);
    }
    throw new RuntimeException("User is not logged in.");
  }

  public static User getUser(String token) {
    return users.get(token);
  }

  public static void clean() {
    users.clear();
  }
}
