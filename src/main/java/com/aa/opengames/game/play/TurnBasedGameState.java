package com.aa.opengames.game.play;

public class TurnBasedGameState implements GameState {

    private int currentPlayerIndex;

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

}
