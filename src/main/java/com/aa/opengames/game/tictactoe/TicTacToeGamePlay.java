package com.aa.opengames.game.tictactoe;

import com.aa.opengames.exceptions.HandledRuntimeException;
import com.aa.opengames.game.play.GamePlayPlayerInfo;
import com.aa.opengames.game.play.GamePlayResult;
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
    private TicTacToeGamePlay(UUID id, UUID tableId, boolean isInitialized, ArrayList<? extends GamePlayPlayerInfo> playersInfo, TurnBasedGameState gameState, GamePlayResult gamePlayResult) {
        super(id, tableId, isInitialized, playersInfo, gameState, gamePlayResult);
    }

    public TicTacToeGamePlayPlayerInfo getCurrentPlayerInfo() {
        int currentPlayerIndex = getGameState().getCurrentPlayerIndex();
        return (TicTacToeGamePlayPlayerInfo) getPlayersInfo().get(currentPlayerIndex);
    }

    public TicTacToeGamePlayPlayerInfo getCurrentPlayerInfoAndCheckUser(User user) {
        if (gameState.isFinished()) {
            throw new HandledRuntimeException("No action can be executed by " + user.getUsername() + " as game " + getId() + " is finished.");
        }
        TicTacToeGamePlayPlayerInfo playerInfo = getCurrentPlayerInfo();
        if (playerInfo.getUsername().equals(user.getUsername())) {
            return playerInfo;
        }
        throw new HandledRuntimeException("User '" + user.getUsername() + "' sent an action during '" + playerInfo.getUsername() + "' turn.");
    }

}
