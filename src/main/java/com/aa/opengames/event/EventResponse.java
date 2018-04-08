package com.aa.opengames.event;

public abstract class EventResponse {
  public enum ResponseStatus {
    SUCCESS, ERROR
  }

  protected final ResponseStatus responseStatus;
  protected final String message;

  public EventResponse(ResponseStatus responseStatus, String message) {
    this.responseStatus = responseStatus;
    this.message = message;
  }


  public ResponseStatus getResponseStatus() {
    return responseStatus;
  }

  public String getMessage() {
    return message;
  }


  public static class EventResponseBuilder<ResponseBuilder> {
    protected ResponseStatus responseStatus;
    protected String message;

    @SuppressWarnings("unchecked")
    public ResponseBuilder responseStatus(ResponseStatus responseStatus) {
      this.responseStatus = responseStatus;
      return (ResponseBuilder)this;
    }

    @SuppressWarnings("unchecked")
    public ResponseBuilder message(String message) {
      this.message = message;
      return (ResponseBuilder)this;
    }
  }
}
