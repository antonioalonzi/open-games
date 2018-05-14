package com.aa.opengames.game.play;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class GameState {
    private boolean finished = false;
}
