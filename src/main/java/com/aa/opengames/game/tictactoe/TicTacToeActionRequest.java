package com.aa.opengames.game.tictactoe;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TicTacToeActionRequest {
    private UUID id;
    private TicTacToeAction action;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class TicTacToeAction {
        private int i;
        private int j;
    }
}
