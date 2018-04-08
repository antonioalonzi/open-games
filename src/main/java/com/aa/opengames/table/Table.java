package com.aa.opengames.table;

import java.util.Objects;
import java.util.UUID;

public class Table {
  public enum Status {
    NEW, IN_PROGRESS, FINISHED
  }

  private UUID id;
  private String game;
  private String owner;
  private Status status;

  public Table(UUID id, String game, String owner, Status status) {
    this.id = id;
    this.game = game;
    this.owner = owner;
    this.status = status;
  }

  public UUID getId() {
    return id;
  }

  public String getGame() {
    return game;
  }

  public String getOwner() {
    return owner;
  }

  public Status getStatus() {
    return status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Table table = (Table) o;
    return Objects.equals(id, table.id);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id);
  }

  public static class TableBuilder {
    private UUID id;
    private String game;
    private String owner;
    private Status status;

    private TableBuilder() {}

    public static TableBuilder tableBuilder() {
      return new TableBuilder();
    }

    public TableBuilder id(UUID id) {
      this.id = id;
      return this;
    }

    public TableBuilder game(String game) {
      this.game = game;
      return this;
    }

    public TableBuilder owner(String owner) {
      this.owner = owner;
      return this;
    }

    public TableBuilder status(Status status) {
      this.status = status;
      return this;
    }

    public Table build() {
      return new Table(id, game, owner, status);
    }
  }
}
