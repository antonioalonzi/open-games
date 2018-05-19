package com.aa.opengames.game.play;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class GamePlay<GS extends GameState> {
    protected UUID id;
    protected UUID tableId;
    protected boolean isInitialized;
    protected ArrayList<? extends GamePlayPlayerInfo> playersInfo;
    protected GS gameState;
    protected GamePlayResult gamePlayResult;

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
