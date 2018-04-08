package com.aa.opengames.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class UserRepository {

  private Map<String, User> users = new HashMap<>();

  public List<User> getAllUsers() {
    return users.entrySet().stream()
        .map(Map.Entry::getValue)
        .collect(Collectors.toList());
  }

  public Optional<User> findByUsername(String username) {
    return Optional.ofNullable(users.get(username));
  }

  public Optional<User> findByToken(String token) {
    return users.entrySet().stream()
        .map(Map.Entry::getValue)
        .filter((user) -> user.getToken().equals(token))
        .findFirst();
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
