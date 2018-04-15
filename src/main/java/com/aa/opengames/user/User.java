package com.aa.opengames.user;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = "id")
@Builder
public class User {
  private String username;
  private String token;
}
