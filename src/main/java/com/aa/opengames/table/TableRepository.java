package com.aa.opengames.table;

import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class TableRepository {

  private Set<Table> tables = new HashSet<>();

  public Set<Table> getAllTables() {
    return tables;
  }

  public void addTable(Table table) {
    tables.add(table);
  }

  public void removeAllTables() {
    tables.clear();
  }
}
