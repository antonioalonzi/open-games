package com.aa.opengames.game;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

@Service
public class GameRepository {

  private Set<Game> games = new HashSet<>();

  public GameRepository() {
    games.add(Game.builder()
        .label("tic-tac-toe")
        .name("Tic Tac Toe")
        .description("Simple implementation of Tic Tac Toe.")
        .minNumPlayers(2)
        .maxNumPlayers(2)
        .build());
  }

  public Set<Game> getAllGames() {
    return games;
  }

  public Optional<Game> getGameByLabel(String label) {
    return games.stream()
        .filter(game -> game.getLabel().equals(label))
        .findFirst();
  }
}
