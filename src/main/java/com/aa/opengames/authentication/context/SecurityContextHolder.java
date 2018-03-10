package com.aa.opengames.authentication.context;

import com.aa.opengames.user.User;
import java.util.HashMap;

public class SecurityContextHolder {

  private static HashMap<String, User> USERS = new HashMap<>();

  public static void addUser(String token, User user) {
    USERS.put(token, user);
  }

  public static User getUser(String token) {
    return USERS.get(token);
  }

}