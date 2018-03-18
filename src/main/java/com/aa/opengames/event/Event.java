package com.aa.opengames.event;

public class Event {
  private String type;
  private Object value;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }


  public static class EventBuilder {
    private String type;
    private Object value;

    public static EventBuilder eventBuilder() {
     return new EventBuilder();
    }

    public EventBuilder type(String type) {
      this.type = type;
      return this;
    }

    public EventBuilder value(Object value) {
      this.value = value;
      return this;
    }

    public Event build() {
      Event event = new Event();
      event.setType(type);
      event.setValue(value);
      return event;
    }
  }
}
