package com.aa.opengames.table.create;

import com.aa.opengames.table.Table;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

@Getter
@Builder
public class TableCreatedEvent {
  public static final String EVENT_TYPE = "table-created-event";

  private UUID id;
  private String game;
  private String owner;
  private Table.Status status;
  @Singular
  private Set<String> joiners;
}
