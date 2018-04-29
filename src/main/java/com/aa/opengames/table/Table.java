package com.aa.opengames.table;

import com.aa.opengames.game.GamePlay;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;

@Getter
@EqualsAndHashCode(of = "id")
@Builder(toBuilder = true)
public class Table {
  public enum Status {
    NEW, IN_PROGRESS, FINISHED, CANCELLED;

    public boolean isActive() {
      return this == NEW || this == IN_PROGRESS;
    }
  }

  private UUID id;
  private LocalDateTime createdDateTime;
  private String game;
  private String owner;
  private Status status;
  private LocalDateTime startedDateTime;
  private LocalDateTime finishedDateTime;
  @Singular
  private Set<String> joiners;
  private GamePlay gamePlay;

  public int getNumOfPlayers() {
    return joiners.size() + 1;
  }
}
