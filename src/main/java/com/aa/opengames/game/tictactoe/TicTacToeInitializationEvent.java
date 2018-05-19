package com.aa.opengames.game.tictactoe;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.UUID;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class TicTacToeInitializationEvent {

  public static final String EVENT_TYPE = "tic-tac-toe-init-event";

  private UUID id;
  private int currentPlayerIndex;
  private ArrayList<PlayerInfo> playersInfo;

  @Getter
  @Builder
  @ToString
  @EqualsAndHashCode
  public static class PlayerInfo {
    private String username;
    private String symbol;
  }

}
