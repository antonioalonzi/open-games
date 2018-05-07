package com.aa.opengames.game.tictactoe;

import com.aa.opengames.game.play.GamePlay;
import com.aa.opengames.game.play.GamePlayPlayerInfo;
import com.aa.opengames.game.play.GameState;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.UUID;

@Getter
public class TicTacToeGamePlay extends GamePlay {

    public static final String TIC_TAC_TOE_LABEL = "tic-tac-toe";

    @Builder(toBuilder = true)
    private TicTacToeGamePlay(UUID id, UUID tableId, boolean isInitialized, ArrayList<? extends GamePlayPlayerInfo> playersInfo, int currentPlayerIndex, GameState gameState) {
        super(id, tableId, isInitialized, playersInfo, currentPlayerIndex, gameState);
    }

}
