package com.aa.opengames.game.tictactoe;

import com.aa.opengames.init.InitController;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class TicTacToeController {

    private final Logger LOGGER = LoggerFactory.getLogger(InitController.class);

    private final TicTacToeGamePlayRepository ticTacToeGamePlayRepository;

    @Autowired
    public TicTacToeController(TicTacToeGamePlayRepository ticTacToeGamePlayRepository) {
        this.ticTacToeGamePlayRepository = ticTacToeGamePlayRepository;
    }

    @Scheduled(fixedRate = 1000)
    public void initialize() {
        Set<TicTacToeGamePlay> gamePlayToInitialize = ticTacToeGamePlayRepository.getAll().stream()
                .filter((gamePlay) -> !gamePlay.isInitialized())
                .collect(Collectors.toSet());

        gamePlayToInitialize.forEach((gamePlay) -> {
            TicTacToeGamePlay initializedGamePlay = gamePlay.toBuilder()
                    .isInitialized(true)
                    .build();

            ticTacToeGamePlayRepository.update(initializedGamePlay);

            LOGGER.info("Initialized TicTacToeGamePlay with id " + gamePlay.getId());
        });
    }
}
