package com.aa.opengames.game;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = "label")
@Builder
public class Game {
  private String label;
  private String name;
  private String description;
  private int minNumPlayers;
  private int maxNumPlayers;
}
