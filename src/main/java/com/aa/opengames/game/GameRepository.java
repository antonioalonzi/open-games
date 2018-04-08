package com.aa.opengames.game;

import static com.aa.opengames.game.Game.GameBuilder.gameBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class GameRepository {

  private Map<String, Game> games = new HashMap<>();

  public GameRepository() {
    games.put("tic-tac-toe", gameBuilder()
        .label("tic-tac-toe")
        .name("Tic Tac Toe")
        .description("Simple implementation of Tic Tac Toe.")
        .build());
  }

  public List<Game> getAllGames() {
    return games.entrySet().stream()
        .map(Map.Entry::getValue)
        .collect(Collectors.toList());
  }
}
