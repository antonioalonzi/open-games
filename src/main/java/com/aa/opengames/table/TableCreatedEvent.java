package com.aa.opengames.table;

import java.util.UUID;

public class TableCreatedEvent {
  public enum Status {
    NEW, IN_PROGRESS, FINISHED
  }

  private UUID id;
  private String game;
  private String owner;
  private Status status;

  public UUID getId() {
    return id;
  }

  public TableCreatedEvent setId(UUID id) {
    this.id = id;
    return this;
  }

  public String getGame() {
    return game;
  }

  public TableCreatedEvent setGame(String game) {
    this.game = game;
    return this;
  }

  public String getOwner() {
    return owner;
  }

  public TableCreatedEvent setOwner(String owner) {
    this.owner = owner;
    return this;
  }

  public Status getStatus() {
    return status;
  }

  public TableCreatedEvent setStatus(Status status) {
    this.status = status;
    return this;
  }

  public static class TableCreatedEventBuilder {
    private UUID id;
    private String game;
    private String owner;
    private Status status;

    private TableCreatedEventBuilder() {}

    public static TableCreatedEventBuilder tableCreatedEventBuilder() {
      return new TableCreatedEventBuilder();
    }

    public TableCreatedEventBuilder id(UUID id) {
      this.id = id;
      return this;
    }

    public TableCreatedEventBuilder game(String game) {
      this.game = game;
      return this;
    }

    public TableCreatedEventBuilder owner(String owner) {
      this.owner = owner;
      return this;
    }

    public TableCreatedEventBuilder status(Status status) {
      this.status = status;
      return this;
    }

    public TableCreatedEventBuilder status(Table.Status status) {
      this.status = Status.valueOf(status.toString());
      return this;
    }

    public TableCreatedEvent build() {
      TableCreatedEvent tableCreatedEvent = new TableCreatedEvent();
      tableCreatedEvent.setId(id);
      tableCreatedEvent.setGame(game);
      tableCreatedEvent.setOwner(owner);
      tableCreatedEvent.setStatus(status);
      return tableCreatedEvent;
    }
  }
}
