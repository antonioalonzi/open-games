package com.aa.opengames.game.play;

import static com.aa.opengames.game.tictactoe.TicTacToeGamePlay.TIC_TAC_TOE_LABEL;

import com.aa.opengames.game.tictactoe.TicTacToeGamePlayFactory;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                ticTacToeGamePlayFactory.create(UUID.randomUUID(), tableId);

            default:
                throw new RuntimeException("Game with label " + label + " is not valid.");
        }
    }
}
