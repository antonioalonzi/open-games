package com.aa.opengames.user;

import java.util.Random;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

  public Optional<User> getUser(String username) {
    if (username.startsWith("guest")) {
      return Optional.of(new User(username + "-" + new Random().nextInt(10000)));
    }
    return Optional.empty();
  }
}
