package com.aa.opengames.game.play;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public GameState gameState;

    public GamePlayPlayerInfo getPlayerInfo(String username) {
        return playersInfo.stream().filter((playerInfo) -> (playerInfo).getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Error username not found"));
    }

    public List<GamePlayPlayerInfo> getOppenentsInfo(String username) {
        return playersInfo.stream().filter((playerInfo) -> !playerInfo.getUsername().equals(username))
                .collect(Collectors.toList());
    }
}
