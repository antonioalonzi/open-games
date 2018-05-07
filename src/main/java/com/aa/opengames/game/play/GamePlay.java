package com.aa.opengames.game.play;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.UUID;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class GamePlay {
    public UUID id;
    public UUID tableId;
    public boolean isInitialized;
    public ArrayList<? extends GamePlayPlayerInfo> playersInfo;
    public int currentPlayerIndex;
    public GameState gameState;
}
