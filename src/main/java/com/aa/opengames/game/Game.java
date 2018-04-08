package com.aa.opengames.game;

public class Game {
  private String label;
  private String name;
  private String description;

  public String getLabel() {
    return label;
  }

  public Game setLabel(String label) {
    this.label = label;
    return this;
  }

  public String getName() {
    return name;
  }

  public Game setName(String name) {
    this.name = name;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public Game setDescription(String description) {
    this.description = description;
    return this;
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
      Game game = new Game();
      game.setLabel(label);
      game.setName(name);
      game.setDescription(description);
      return game;
    }
  }
}
