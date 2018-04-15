package com.aa.opengames.table.create;

import com.aa.opengames.event.EventResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateTableResponse extends EventResponse {
  public static final String EVENT_TYPE = "create-table-response";

  @Builder
  private CreateTableResponse(ResponseStatus responseStatus, String message) {
    super(responseStatus, message);
  }

}
