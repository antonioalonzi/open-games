package com.aa.opengames.game;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GamePublishedEvent {
  private String label;
  private String name;
  private String description;
}
