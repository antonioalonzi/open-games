package com.aa.opengames;

import com.aa.opengames.user.User;
import com.aa.opengames.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GenerateTestDatabase {

  @Autowired
  private UserRepository userRepository;

  @Test
  public void generate() {
    userRepository.deleteAll();
    userRepository.save(new User("guest", "password"));
  }
}
