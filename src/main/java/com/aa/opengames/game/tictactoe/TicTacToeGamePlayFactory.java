package com.aa.opengames.game.tictactoe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TicTacToeGamePlayFactory {

    private final TicTacToeGamePlayRepository ticTacToeGamePlayRepository;

    @Autowired
    public TicTacToeGamePlayFactory(TicTacToeGamePlayRepository ticTacToeGamePlayRepository) {
        this.ticTacToeGamePlayRepository = ticTacToeGamePlayRepository;
    }

    public TicTacToeGamePlay create(UUID gamePlayId, UUID tableId) {
        TicTacToeGamePlay ticTacToeGamePlay = TicTacToeGamePlay.builder()
                .id(gamePlayId)
                .tableId(tableId)
                .gameState(new TicTacToeGameState())
                .build();
        ticTacToeGamePlayRepository.add(ticTacToeGamePlay);
        return ticTacToeGamePlay;
    }

}
