package com.aa.opengames.table;

import com.aa.opengames.event.EventResponse;

public class CreateTableResponse extends EventResponse {

  public CreateTableResponse(ResponseStatus responseStatus, String message) {
    super(responseStatus, message);
  }

  public static class CreateTableResponseBuilder extends EventResponseBuilder<CreateTableResponseBuilder> {
    private CreateTableResponseBuilder() {}

    public static CreateTableResponseBuilder createTableResponseBuilder() {
      return new CreateTableResponseBuilder();
    }

    public CreateTableResponse build() {
      return new CreateTableResponse(responseStatus, message);
    }
  }
}
