package com.aa.opengames.game.play;

import com.aa.opengames.exceptions.HandledRuntimeException;
import com.aa.opengames.game.tictactoe.TicTacToeGamePlayFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.aa.opengames.game.tictactoe.TicTacToeGamePlay.TIC_TAC_TOE_LABEL;

@Service
public class GamePlayFactory {

    private final TicTacToeGamePlayFactory ticTacToeGamePlayFactory;

    @Autowired
    public GamePlayFactory(TicTacToeGamePlayFactory ticTacToeGamePlayFactory) {
        this.ticTacToeGamePlayFactory = ticTacToeGamePlayFactory;
    }

    public GamePlay createGamePlay(String label, UUID tableId) {
        switch (label) {
            case TIC_TAC_TOE_LABEL:
                return ticTacToeGamePlayFactory.create(UUID.randomUUID(), tableId);

            default:
                throw new HandledRuntimeException("Game with label " + label + " is not valid.");
        }
    }
}
