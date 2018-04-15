package com.aa.opengames.authentication.login;

import com.aa.opengames.event.EventResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse extends EventResponse {
  private final UserDetails userDetails;

  @Builder
  private LoginResponse(ResponseStatus responseStatus, String message, UserDetails userDetails) {
    super(responseStatus, message);
    this.userDetails = userDetails;
  }

  @Getter
  @Builder
  public static class UserDetails {
    private final String token;
    private final String username;
  }
}
