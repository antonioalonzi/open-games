package com.aa.opengames.table;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TableUpdatedEvent {
  public static final String EVENT_TYPE = "table-updated-event";

  private UUID id;
  private Table.Status status;
}
