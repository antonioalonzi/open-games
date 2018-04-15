package com.aa.opengames.table;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TableCreatedEvent {
  private UUID id;
  private String game;
  private String owner;
  private Table.Status status;
}
