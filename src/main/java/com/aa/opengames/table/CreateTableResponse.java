package com.aa.opengames.table;

import com.aa.opengames.event.EventResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateTableResponse extends EventResponse {

  @Builder
  private CreateTableResponse(ResponseStatus responseStatus, String message) {
    super(responseStatus, message);
  }

}
