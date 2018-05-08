package com.aa.opengames.game.tictactoe;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TicTacToaBoardUtilsTest {

    @Test
    public void isGameFinishedShouldReturnFalseIfEmptyBoard() {
        // Given
        String [][] board = new String[][] {
                new String[] {"", "", ""},
                new String[] {"", "", ""},
                new String[] {"", "", ""}
        };

        // When
        TicTacToaBoardUtils.GameFinishStatus finishedStatus = TicTacToaBoardUtils.isGameFinished(board);

        // Then
        assertThat(finishedStatus).isEqualTo(TicTacToaBoardUtils.GameFinishStatus.builder()
                .finished(false)
                .winningSymbol("")
                .build());
    }

    @Test
    public void isGameFinishedShouldReturnFalseIfNotFinishedBoard() {
        // Given
        String [][] board = new String[][] {
                new String[] {"X", "", ""},
                new String[] {"", "O", "X"},
                new String[] {"", "", "O"}
        };

        // When
        TicTacToaBoardUtils.GameFinishStatus finishedStatus = TicTacToaBoardUtils.isGameFinished(board);

        // Then
        assertThat(finishedStatus).isEqualTo(TicTacToaBoardUtils.GameFinishStatus.builder()
                .finished(false)
                .winningSymbol("")
                .build());
    }

    @Test
    public void isGameFinishedShouldReturnTrueIfWonHorizontally() {
        // Given
        String [][] board = new String[][] {
                new String[] {"X", "O", ""},
                new String[] {"X", "X", "X"},
                new String[] {"", "O", "O"}
        };

        // When
        TicTacToaBoardUtils.GameFinishStatus finishedStatus = TicTacToaBoardUtils.isGameFinished(board);

        // Then
        assertThat(finishedStatus).isEqualTo(TicTacToaBoardUtils.GameFinishStatus.builder()
                .finished(true)
                .winningSymbol("X")
                .build());
    }

    @Test
    public void isGameFinishedShouldReturnTrueIfWonVertically() {
        // Given
        String [][] board = new String[][] {
                new String[] {"X", "O", ""},
                new String[] {"X", "O", "X"},
                new String[] {"", "O", "X"}
        };

        // When
        TicTacToaBoardUtils.GameFinishStatus finishedStatus = TicTacToaBoardUtils.isGameFinished(board);

        // Then
        assertThat(finishedStatus).isEqualTo(TicTacToaBoardUtils.GameFinishStatus.builder()
                .finished(true)
                .winningSymbol("O")
                .build());
    }

    @Test
    public void isGameFinishedShouldReturnTrueIfWonDiagonally1() {
        // Given
        String [][] board = new String[][] {
                new String[] {"X", "O", ""},
                new String[] {"X", "X", "O"},
                new String[] {"", "O", "X"}
        };

        // When
        TicTacToaBoardUtils.GameFinishStatus finishedStatus = TicTacToaBoardUtils.isGameFinished(board);

        // Then
        assertThat(finishedStatus).isEqualTo(TicTacToaBoardUtils.GameFinishStatus.builder()
                .finished(true)
                .winningSymbol("X")
                .build());
    }

    @Test
    public void isGameFinishedShouldReturnTrueIfWonDiagonally2() {
        // Given
        String [][] board = new String[][] {
                new String[] {"X", "X", "O"},
                new String[] {"X", "O", "X"},
                new String[] {"O", "O", "X"}
        };

        // When
        TicTacToaBoardUtils.GameFinishStatus finishedStatus = TicTacToaBoardUtils.isGameFinished(board);

        // Then
        assertThat(finishedStatus).isEqualTo(TicTacToaBoardUtils.GameFinishStatus.builder()
                .finished(true)
                .winningSymbol("O")
                .build());
    }

    @Test
    public void isGameFinishedShouldReturnTrueIfDraw() {
        // Given
        String [][] board = new String[][] {
                new String[] {"O", "X", "O"},
                new String[] {"X", "O", "X"},
                new String[] {"X", "O", "X"}
        };

        // When
        TicTacToaBoardUtils.GameFinishStatus finishedStatus = TicTacToaBoardUtils.isGameFinished(board);

        // Then
        assertThat(finishedStatus).isEqualTo(TicTacToaBoardUtils.GameFinishStatus.builder()
                .finished(true)
                .winningSymbol("")
                .build());
    }

}