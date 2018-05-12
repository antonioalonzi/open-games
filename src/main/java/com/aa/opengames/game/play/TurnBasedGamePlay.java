package com.aa.opengames.game.play;

import lombok.Getter;

import java.util.ArrayList;
import java.util.UUID;

@Getter
public class TurnBasedGamePlay extends GamePlay<TurnBasedGameState> {

    public TurnBasedGamePlay(UUID id, UUID tableId, boolean isInitialized, ArrayList<? extends GamePlayPlayerInfo> playersInfo, TurnBasedGameState gameState) {
        super(id, tableId, isInitialized, playersInfo, gameState);
    }
}
