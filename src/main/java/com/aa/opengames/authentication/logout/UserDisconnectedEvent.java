package com.aa.opengames.authentication.logout;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDisconnectedEvent {
  public static final String EVENT_TYPE = "user-disconnected-event";

  private String username;
}
