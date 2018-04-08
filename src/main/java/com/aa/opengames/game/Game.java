package com.aa.opengames.game;

import java.util.Objects;

public class Game {
  private String label;
  private String name;
  private String description;

  public Game(String label, String name, String description) {
    this.label = label;
    this.name = name;
    this.description = description;
  }

  public String getLabel() {
    return label;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Game game = (Game) o;
    return Objects.equals(label, game.label);
  }

  @Override
  public int hashCode() {
    return Objects.hash(label);
  }

  public static class GameBuilder {
    private String label;
    private String name;
    private String description;

    private GameBuilder() {}

    public static GameBuilder gameBuilder() {
      return new GameBuilder();
    }

    public GameBuilder label(String label) {
      this.label = label;
      return this;
    }

    public GameBuilder name(String name) {
      this.name = name;
      return this;
    }

    public GameBuilder description(String description) {
      this.description = description;
      return this;
    }

    public Game build() {
      return new Game(label, name, description);
    }
  }
}
