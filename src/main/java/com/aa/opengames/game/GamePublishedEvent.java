package com.aa.opengames.game;

public class GamePublishedEvent {
  private String label;
  private String name;
  private String description;

  public String getLabel() {
    return label;
  }

  public GamePublishedEvent setLabel(String label) {
    this.label = label;
    return this;
  }

  public String getName() {
    return name;
  }

  public GamePublishedEvent setName(String name) {
    this.name = name;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public GamePublishedEvent setDescription(String description) {
    this.description = description;
    return this;
  }

  public static class GamePublishedEventBuilder {
    private String label;
    private String name;
    private String description;

    private GamePublishedEventBuilder() {}

    public static GamePublishedEventBuilder gamePublishedEventBuilder() {
      return new GamePublishedEventBuilder();
    }

    public GamePublishedEventBuilder label(String label) {
      this.label = label;
      return this;
    }

    public GamePublishedEventBuilder name(String name) {
      this.name = name;
      return this;
    }

    public GamePublishedEventBuilder description(String description) {
      this.description = description;
      return this;
    }

    public GamePublishedEvent build() {
      GamePublishedEvent gamePublishedEvent = new GamePublishedEvent();
      gamePublishedEvent.setLabel(label);
      gamePublishedEvent.setName(name);
      gamePublishedEvent.setDescription(description);
      return gamePublishedEvent;
    }
  }
}
