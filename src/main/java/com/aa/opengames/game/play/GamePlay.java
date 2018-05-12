package com.aa.opengames.game.play;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class GamePlay<GS extends GameState> {
    public UUID id;
    public UUID tableId;
    public boolean isInitialized;
    public ArrayList<? extends GamePlayPlayerInfo> playersInfo;
    public GS gameState;

    public List<String> getPlayersUsername() {
        return playersInfo.stream()
                .map(GamePlayPlayerInfo::getUsername)
                .collect(Collectors.toList());
    }

    public GamePlayPlayerInfo getPlayerInfo(String username) {
        return playersInfo.stream().filter((playerInfo) -> (playerInfo).getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Error username not found"));
    }
}
