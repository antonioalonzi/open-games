package com.aa.opengames.user;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

  public Optional<User> getUser(String username) {
    if (username.equals("guest")) {
      return Optional.of(new User("guest"));
    }
    return Optional.empty();
  }
}
