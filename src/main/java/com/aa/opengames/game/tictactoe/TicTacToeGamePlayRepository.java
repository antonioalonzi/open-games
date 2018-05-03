package com.aa.opengames.game.tictactoe;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class TicTacToeGamePlayRepository {

    private Set<TicTacToeGamePlay> ticTacToeGamePlays = new HashSet<>();

    public void add(TicTacToeGamePlay ticTacToeGamePlay) {
        ticTacToeGamePlays.add(ticTacToeGamePlay);
    }

    public Set<TicTacToeGamePlay> getAll() {
        return ticTacToeGamePlays;
    }

    public Optional<TicTacToeGamePlay> getById(UUID id) {
        return ticTacToeGamePlays.stream().filter(ticTacToeGamePlay -> ticTacToeGamePlay.getId().equals(id)).findFirst();
    }

    public void update(TicTacToeGamePlay gamePlay) {
        if (ticTacToeGamePlays.contains(gamePlay)) {
            ticTacToeGamePlays.remove(gamePlay);
            ticTacToeGamePlays.add(gamePlay);
        } else {
            throw new RuntimeException("TicTacToeGamePlay with id " + gamePlay.getId() + " does not exist.");
        }
    }
}
