package com.aa.opengames.game.tictactoe;

import com.aa.opengames.game.play.GamePlayPlayerInfo;
import com.aa.opengames.game.play.TurnBasedGamePlay;
import com.aa.opengames.game.play.TurnBasedGameState;
import com.aa.opengames.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.UUID;

@Getter
public class TicTacToeGamePlay extends TurnBasedGamePlay {

    public static final String TIC_TAC_TOE_LABEL = "tic-tac-toe";

    @Builder(toBuilder = true)
    private TicTacToeGamePlay(UUID id, UUID tableId, boolean isInitialized, ArrayList<? extends GamePlayPlayerInfo> playersInfo, TurnBasedGameState gameState) {
        super(id, tableId, isInitialized, playersInfo, gameState);
    }

    public TicTacToeGamePlayPlayerInfo getCurrentPlayerInfo() {
        int currentPlayerIndex = gameState.getCurrentPlayerIndex();
        return (TicTacToeGamePlayPlayerInfo)playersInfo.get(currentPlayerIndex);
    }

    public TicTacToeGamePlayPlayerInfo getCurrentPlayerInfoAndCheckUser(User user) {
        TicTacToeGamePlayPlayerInfo playerInfo = getCurrentPlayerInfo();
        if (playerInfo.getUsername().equals(user.getUsername())) {
            return playerInfo;
        }
        throw new RuntimeException("User '" + user.getUsername() + "' sent an action during '" + playerInfo.getUsername() + "' turn.");
    }

}
