package com.aa.opengames.game.tictactoe;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;

import static com.aa.opengames.game.tictactoe.TicTacToeGamePlayPlayerInfo.TIC_TAC_TOE_SYMBOL_EMPTY;

public class TicTacToaBoardUtils {

    public static GameFinishStatus isGameFinished(String[][] board) {
        ArrayList<GameFinishStatus> results = new ArrayList<>();

        // Horizontal
        results.add(tris(board[0][0], board[0][1], board[0][2]));
        results.add(tris(board[1][0], board[1][1], board[1][2]));
        results.add(tris(board[2][0], board[2][1], board[2][2]));
        // Vertical
        results.add(tris(board[0][0], board[1][0], board[2][0]));
        results.add(tris(board[0][1], board[1][1], board[2][1]));
        results.add(tris(board[0][2], board[1][2], board[2][2]));
        // Diagonal
        results.add(tris(board[0][0], board[1][1], board[2][2]));
        results.add(tris(board[2][0], board[1][1], board[0][2]));

        results.add(draw(board));

        return results.stream()
                .filter(GameFinishStatus::isFinished)
                .findFirst()
                .orElse(results.get(0));
    }

    private static GameFinishStatus tris(String cell1, String cell2, String cell3) {
        if (!cell1.equals(TIC_TAC_TOE_SYMBOL_EMPTY) && cell1.equals(cell2) && cell1.equals(cell3)) {
            return GameFinishStatus.builder()
                    .finished(true)
                    .winningSymbol(cell1)
                    .build();
        }

        return GameFinishStatus.builder()
                .finished(false)
                .winningSymbol(TIC_TAC_TOE_SYMBOL_EMPTY)
                .build();
    }

    private static GameFinishStatus draw(String[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j].equals(TIC_TAC_TOE_SYMBOL_EMPTY)) {
                    return GameFinishStatus.builder()
                            .finished(false)
                            .winningSymbol(TIC_TAC_TOE_SYMBOL_EMPTY)
                            .build();
                }
            }
        }

        return GameFinishStatus.builder()
                .finished(true)
                .winningSymbol(TIC_TAC_TOE_SYMBOL_EMPTY)
                .build();
    }

    @Builder
    @Getter
    @EqualsAndHashCode
    @ToString
    public static class GameFinishStatus {
        private boolean finished;
        private String winningSymbol;
    }

}
