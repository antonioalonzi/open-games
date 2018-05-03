package com.aa.opengames.game.play;

import java.util.ArrayList;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class GamePlay {
    public UUID id;
    public UUID tableId;
    public boolean isInitialized;
    public ArrayList<? extends GamePlayPlayerInfo> playersInfo;
    public int currentPlayerIndex;
}
