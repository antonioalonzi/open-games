package com.aa.opengames.table.leave;

import com.aa.opengames.event.EventResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LeaveTableResponse extends EventResponse {
  public static final String EVENT_TYPE = "leave-table-response";

  @Builder
  private LeaveTableResponse(ResponseStatus responseStatus, String message) {
    super(responseStatus, message);
  }

}
