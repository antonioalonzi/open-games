package com.aa.opengames.table;

import java.util.UUID;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = "id")
@Builder
public class Table {
  public enum Status {
    NEW, IN_PROGRESS, FINISHED
  }

  private UUID id;
  private String game;
  private String owner;
  private Status status;
}
