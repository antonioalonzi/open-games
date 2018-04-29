package com.aa.opengames.game.play;

import static com.aa.opengames.game.tictactoe.TicTacToeGamePlay.TIC_TAC_TOE_LABEL;

import com.aa.opengames.game.tictactoe.TicTacToeGamePlay;
import com.aa.opengames.game.tictactoe.TicTacToeGamePlayRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GamePlayFactory {

    private final TicTacToeGamePlayRepository ticTacToeGamePlayRepository;

    @Autowired
    public GamePlayFactory(TicTacToeGamePlayRepository ticTacToeGamePlayRepository) {
        this.ticTacToeGamePlayRepository = ticTacToeGamePlayRepository;
    }

    public GamePlay createGamePlay(String label, UUID tableId) {
        switch (label) {
            case TIC_TAC_TOE_LABEL:
                TicTacToeGamePlay ticTacToeGamePlay = TicTacToeGamePlay.builder()
                        .id(UUID.randomUUID())
                        .tableId(tableId)
                        .build();
                ticTacToeGamePlayRepository.add(ticTacToeGamePlay);
                return ticTacToeGamePlay;

            default:
                throw new RuntimeException("Game with label " + label + " is not valid.");
        }
    }
}
