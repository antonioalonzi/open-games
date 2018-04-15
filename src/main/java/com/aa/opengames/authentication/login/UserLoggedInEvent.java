package com.aa.opengames.authentication.login;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoggedInEvent {
  public static final String EVENT_TYPE = "user-logged-in-event";

  private String username;
}
