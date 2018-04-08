package com.aa.opengames.table;

public class CreateTableRequest {
  private String game;

  public String getGame() {
    return game;
  }

  public CreateTableRequest setGame(String game) {
    this.game = game;
    return this;
  }

  public static class CreateTableRequestBuilder {
    private String game;

    private CreateTableRequestBuilder() {}

    public static CreateTableRequestBuilder createTableRequestBuilder() {
      return new CreateTableRequestBuilder();
    }

    public CreateTableRequestBuilder game(String game) {
      this.game = game;
      return this;
    }

    public CreateTableRequest build() {
      CreateTableRequest createTableRequest = new CreateTableRequest();
      createTableRequest.setGame(game);
      return createTableRequest;
    }
  }
}
