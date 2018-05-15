package com.aa.opengames.table;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.aa.opengames.exceptions.HandledRuntimeException;
import org.springframework.stereotype.Service;

@Service
public class TableRepository {

    private Set<Table> tables = new HashSet<>();

    public Set<Table> getAllTables() {
        return tables;
    }

    public Table getTableById(UUID id) {
        return tables.stream()
                .filter(table -> table.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cannot find table with id " + id));
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
            throw new HandledRuntimeException("Table with id " + table.getId() + " does not exist.");
        }
    }

    public void removeAllTables() {
        tables.clear();
    }
}
