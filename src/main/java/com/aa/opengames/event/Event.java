package com.aa.opengames.event;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Event {
  private String type;
  private Object value;
}
