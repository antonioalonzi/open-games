package com.aa.opengames.user;

import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserSessionRepository extends MongoRepository<UserSession, String> {

  UserSession findByUuid(UUID uuid);

}
