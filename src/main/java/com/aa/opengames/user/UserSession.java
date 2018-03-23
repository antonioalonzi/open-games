package com.aa.opengames.user;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserSession {
  @Id
  private String id;
  private String userId;
  private UUID uuid;
  private LocalDateTime loggedInAt;
}
