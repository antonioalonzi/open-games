package com.aa.opengames.game.play;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GamePlayResult {
    private GamePlayResultType gamePlayResultType;
    private String winner;

    public enum GamePlayResultType {
        DRAW, WIN_LOSS
    }
}
