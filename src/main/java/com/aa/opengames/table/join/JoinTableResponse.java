package com.aa.opengames.table.join;

import com.aa.opengames.event.EventResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class JoinTableResponse extends EventResponse {
  public static final String EVENT_TYPE = "join-table-response";

  @Builder
  private JoinTableResponse(ResponseStatus responseStatus, String message) {
    super(responseStatus, message);
  }

}
