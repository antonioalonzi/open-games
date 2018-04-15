package com.aa.opengames.game;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GamePublishedEvent {
  public static final String EVENT_TYPE = "game-published-event";

  private String label;
  private String name;
  private String description;
  private int minNumPlayers;
  private int maxNumPlayers;
}
