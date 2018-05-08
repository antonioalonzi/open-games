package com.aa.opengames.game.tictactoe;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TicTacToeFinishEvent {

  public static final String EVENT_TYPE = "tic-tac-toe-finish-event";

  private String winningSymbol;

}
