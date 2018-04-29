package com.aa.opengames.game.tictactoe;

import com.aa.opengames.game.GamePlay;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TicTacToeGamePlay extends GamePlay {

    public static final String TIC_TAC_TOE_LABEL = "tic-tac-toe";

    @Builder(toBuilder = true)
    private TicTacToeGamePlay(UUID id, UUID tableId, boolean isInitialized) {
        super(id, tableId, isInitialized);
    }

}
