package com.aa.opengames.game.tictactoe;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class TicTacToeUpdateEvent {

  public static final String EVENT_TYPE = "tic-tac-toe-update-event";

  private UUID gameId;
  private TicTacToeGameState gameState;

}
