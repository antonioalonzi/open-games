package com.aa.opengames.table;

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
    NEW, IN_PROGRESS, FINISHED, CANCELLED
  }

  private UUID id;
  private String game;
  private String owner;
  private Status status;
  @Singular
  private Set<String> joiners;
}
