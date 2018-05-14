package com.aa.opengames.game.play;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TurnBasedGameState extends GameState {

    private int currentPlayerIndex;

    public void updateToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 2;
    }
}
