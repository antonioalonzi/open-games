package com.aa.opengames.game.tictactoe;

import com.aa.opengames.game.play.GamePlayPlayerInfo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TicTacToeGamePlayPlayerInfo extends GamePlayPlayerInfo {

    public static final String TIC_TAC_TOE_SYMBOL_X = "X";
    public static final String TIC_TAC_TOE_SYMBOL_O = "O";

    private final String symbol;

    @Builder(toBuilder = true)
    public TicTacToeGamePlayPlayerInfo(String username, String symbol) {
        super(username);
        this.symbol = symbol;
    }

}
