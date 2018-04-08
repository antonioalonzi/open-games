package com.aa.opengames.utils;

import static com.aa.opengames.user.User.UserBuilder.userBuilder;

import com.aa.opengames.authentication.context.SecurityContextHolder;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

public class TestUtils {

  public static SimpMessageHeaderAccessor sessionHeader(String sessionId) {
    SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create();
    headerAccessor.setSessionId(sessionId);
    return headerAccessor;
  }

  public static void loginUser(SimpMessageHeaderAccessor header, String username) {
    String token = header.getSessionId();
    SecurityContextHolder.addUser(token, userBuilder().token(token).username(username).build());
  }
}