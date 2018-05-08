package com.aa.opengames.game.tictactoe;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.UUID;

@Getter
@Builder
public class TicTacToeInitializationEvent {

  public static final String EVENT_TYPE = "tic-tac-toe-init-event";

  private UUID id;
  private int currentPlayerIndex;
  private ArrayList<PlayerInfo> playersInfo;

  @Getter
  @Builder
  public static class PlayerInfo {
    private String username;
    private String symbol;
  }

}
