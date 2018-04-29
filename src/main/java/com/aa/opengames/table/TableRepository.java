package com.aa.opengames.table;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class TableRepository {

  private Set<Table> tables = new HashSet<>();

  public Set<Table> getAllTables() {
    return tables;
  }

  public Optional<Table> getTableById(UUID id) {
    return tables.stream()
        .filter(table -> table.getId().equals(id))
        .findFirst();
  }

  public Optional<Table> getActiveTableForUser(String username) {
    return tables.stream()
        .filter(table -> table.getStatus().isActive())
        .filter(table -> table.getOwner().equals(username) || table.getJoiners().contains(username))
        .findFirst();
  }


  public void addTable(Table table) {
    tables.add(table);
  }

  public void updateTable(Table table) {
    if (tables.contains(table)) {
      tables.remove(table);
      tables.add(table);
    } else {
      throw new RuntimeException("Table with id " + table.getId() + " does not exist.");
    }
  }

  public void removeAllTables() {
    tables.clear();
  }
}
