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
    protected UUID id;
    protected UUID tableId;
    protected boolean isInitialized;
    protected ArrayList<? extends GamePlayPlayerInfo> playersInfo;
    protected int currentPlayerIndex;
}
