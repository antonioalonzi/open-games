package com.aa.opengames.game.tictactoe;

import com.aa.opengames.game.play.TurnBasedGameState;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class TicTacToeGameState extends TurnBasedGameState {

    private String[][] board = new String[][] {
            new String[] {"", "", ""},
            new String[] {"", "", ""},
            new String[] {"", "", ""}
    };

    public String[][] getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

    public String getSymbol(int i, int j) {
        return board[i][j];
    }

    public void setSymbol(int i, int j, String symbol) {
        board[i][j] = symbol;
    }

}
